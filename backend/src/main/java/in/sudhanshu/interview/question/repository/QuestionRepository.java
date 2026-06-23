package in.sudhanshu.interview.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sudhanshu.interview.question.entity.Question;

public interface QuestionRepository
        extends JpaRepository<Question, Long> {
}