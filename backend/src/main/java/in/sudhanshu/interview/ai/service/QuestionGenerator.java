package in.sudhanshu.interview.ai.service;

import java.util.List;

import in.sudhanshu.interview.ai.dto.GeneratedQuestion;

public interface QuestionGenerator {

    List<GeneratedQuestion> generateQuestions(String resumeText);
}