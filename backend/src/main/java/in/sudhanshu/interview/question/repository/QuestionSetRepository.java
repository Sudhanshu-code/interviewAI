package in.sudhanshu.interview.question.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sudhanshu.interview.question.entity.QuestionSet;
import in.sudhanshu.interview.resume.entity.Resume;

public interface QuestionSetRepository
                extends JpaRepository<QuestionSet, Long> {

        List<QuestionSet> findByResume(Resume resume);
        
        Optional<QuestionSet> findById(Long id);
}