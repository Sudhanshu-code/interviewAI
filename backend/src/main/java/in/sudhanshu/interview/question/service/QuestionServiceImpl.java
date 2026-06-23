package in.sudhanshu.interview.question.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import in.sudhanshu.interview.ai.dto.GeneratedQuestion;
import in.sudhanshu.interview.ai.service.QuestionGenerator;
import in.sudhanshu.interview.exception.ResourceNotFoundException;
import in.sudhanshu.interview.question.dto.QuestionGenerationResponse;
import in.sudhanshu.interview.question.dto.QuestionResponse;
import in.sudhanshu.interview.question.dto.QuestionSetResponse;
import in.sudhanshu.interview.question.entity.Question;
import in.sudhanshu.interview.question.entity.QuestionSet;
import in.sudhanshu.interview.question.repository.QuestionRepository;
import in.sudhanshu.interview.question.repository.QuestionSetRepository;
import in.sudhanshu.interview.resume.entity.Resume;
import in.sudhanshu.interview.resume.repository.ResumeRepository;
import in.sudhanshu.interview.user.entity.User;
import in.sudhanshu.interview.user.service.CurrentUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

        private final QuestionSetRepository questionSetRepository;

        private final CurrentUserService currentUserService;
        private final ResumeRepository resumeRepository;
        private final QuestionGenerator questionGenerator;
        private final QuestionRepository questionRepository;

        @Override
        public QuestionSetResponse getQuestionSet(Long questionSetId) {

                QuestionSet questionSet = questionSetRepository
                                .findById(questionSetId)
                                .orElseThrow(
                                                () -> new ResourceNotFoundException(
                                                                "Question set not found"));

                return new QuestionSetResponse(
                                questionSet.getId(),
                                questionSet.getGeneratedAt(),
                                questionSet.getQuestions()
                                                .stream()
                                                .map(question -> new QuestionResponse(
                                                                question.getId(),
                                                                question.getQuestionText(),
                                                                question.getDifficulty().name(),
                                                                question.getCategory().name()))
                                                .toList());
        }

        @Override
        @Transactional
        public QuestionGenerationResponse generateQuestions(Long resumeId) {
                User currentUser = currentUserService.getCurrentUser();
                Resume resume = resumeRepository.findById(resumeId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "No resume found"));

                if (!resume.getUser().getId().equals(currentUser.getId())) {
                        throw new ResourceNotFoundException("Resume not found");
                }
                List<GeneratedQuestion> generatedQuestions = questionGenerator
                                .generateQuestions(resume.getExtractedText());

                QuestionSet questionSet = QuestionSet.builder()
                                .generatedAt(LocalDateTime.now())
                                .resume(resume)
                                .build();

                QuestionSet savedQuestionSet = questionSetRepository.save(questionSet);

                List<Question> questions = generatedQuestions.stream()
                                .map(gq -> Question.builder()
                                                .questionText(gq.question())
                                                .difficulty(gq.difficulty())
                                                .category(gq.category())
                                                .questionSet(savedQuestionSet)
                                                .build())
                                .toList();

                questionRepository.saveAll(questions);

                return new QuestionGenerationResponse(savedQuestionSet.getId(), questions.size());
        }

}
