package in.sudhanshu.interview.resume.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import in.sudhanshu.interview.resume.dto.ResumeResponse;
import in.sudhanshu.interview.resume.dto.ResumeTextResponse;

public interface ResumeService {
    ResumeResponse uploadResume(MultipartFile file);

    List<ResumeResponse> getMyResumes();

    void deleteResume(Long resumeId);

    ResumeTextResponse getResumeText(Long resumeId);
}
