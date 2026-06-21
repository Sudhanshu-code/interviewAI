package in.sudhanshu.interview.resume.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.sudhanshu.interview.exception.BadRequestException;
import in.sudhanshu.interview.resume.dto.ResumeResponse;
import in.sudhanshu.interview.resume.dto.ResumeTextResponse;
import in.sudhanshu.interview.resume.service.ResumeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public ResumeResponse uploadResume(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestException(
                    "File cannot be empty");
        }
        if (!"application/pdf".equals(
                file.getContentType())) {

            throw new BadRequestException(
                    "Only PDF files are allowed");
        }

        if (file.getSize() > 5 * 1024 * 1024) {

            throw new BadRequestException(
                    "Maximum file size is 5 MB");
        }
        return resumeService.uploadResume(file);
    }

    @GetMapping
    public List<ResumeResponse> getMyResumes() {

        return resumeService
                .getMyResumes();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Long id) {

        resumeService.deleteResume(id);

        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/{id}/text")
    public ResumeTextResponse getResumeText(@PathVariable Long id) {

        return resumeService.getResumeText(id);
    }
}
