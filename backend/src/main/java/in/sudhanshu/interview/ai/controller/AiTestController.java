package in.sudhanshu.interview.ai.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sudhanshu.interview.ai.dto.GeneratedQuestion;
import in.sudhanshu.interview.ai.service.QuestionGenerator;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AiTestController {

    private final QuestionGenerator questionGenerator;

    @GetMapping("/api/test/gemini")
    public List<GeneratedQuestion> testGemini() {

        return questionGenerator.generateQuestions(
                "Java Spring Boot Developer with React and MySQL experience");

    }
}