package in.sudhanshu.interview.question.service;

import in.sudhanshu.interview.question.dto.QuestionGenerationResponse;
import in.sudhanshu.interview.question.dto.QuestionSetResponse;

public interface QuestionService {

    QuestionSetResponse getQuestionSet(Long questionSetId);

    QuestionGenerationResponse generateQuestions(Long resumeId);
}