package in.sudhanshu.interview.resume.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sudhanshu.interview.resume.entity.Resume;
import in.sudhanshu.interview.user.entity.User;

public interface ResumeRepository
        extends JpaRepository<Resume, Long> {

    List<Resume> findByUser(User user);
}