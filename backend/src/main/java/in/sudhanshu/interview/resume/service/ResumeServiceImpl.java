package in.sudhanshu.interview.resume.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.sudhanshu.interview.exception.BadRequestException;
import in.sudhanshu.interview.exception.ResourceNotFoundException;
import in.sudhanshu.interview.resume.dto.ResumeResponse;
import in.sudhanshu.interview.resume.entity.Resume;
import in.sudhanshu.interview.resume.repository.ResumeRepository;
import in.sudhanshu.interview.user.entity.User;
import in.sudhanshu.interview.user.service.CurrentUserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;

    private final CurrentUserService currentUserService;

    private final FileStorageService fileStorageService;

    @Override
    public ResumeResponse uploadResume(MultipartFile file) {

        try {
            User user = currentUserService.getCurrentUser();

            String storedFileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

            Path uploadPath = fileStorageService.getUploadPath();

            Path targetLocation = uploadPath.resolve(storedFileName);

            Files.copy(file.getInputStream(), targetLocation);

            Resume resume = Resume.builder()
                    .originalFileName(file.getOriginalFilename())
                    .filePath(targetLocation.toString())
                    .storedFileName(storedFileName)
                    .fileSize(file.getSize())
                    .uploadedAt(LocalDateTime.now())
                    .user(user)
                    .build();

            Resume saved = resumeRepository.save(resume);

            return new ResumeResponse(saved.getId(), saved.getOriginalFileName(), saved.getFileSize());

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to upload file");
        }
    }

    @Override
    public List<ResumeResponse> getMyResumes() {

        User user = currentUserService.getCurrentUser();

        return resumeRepository.findByUser(user).stream()
                .map(resume -> new ResumeResponse(resume.getId(), resume.getOriginalFileName(), resume.getFileSize()))
                .toList();
    }

    @Override
    public void deleteResume(Long resumeId) {

        User currentUser = currentUserService.getCurrentUser();

        Resume resume = resumeRepository
                .findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

        if (!resume.getUser().getId().equals(currentUser.getId())) {
            throw new BadRequestException("You do not own this resume");
        }

        try {
            Files.deleteIfExists(Path.of(resume.getFilePath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file");
        }

        resumeRepository.delete(resume);
    }
}
