package in.sudhanshu.interview.question.controller;

import org.springframework.web.bind.annotation.*;

import in.sudhanshu.interview.question.dto.QuestionGenerationResponse;
import in.sudhanshu.interview.question.dto.QuestionSetResponse;
import in.sudhanshu.interview.question.service.QuestionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/sets/{id}")
    public QuestionSetResponse getQuestionSet(@PathVariable Long id) {

        return questionService.getQuestionSet(id);
    }

    @PostMapping("/generate/{resumeId}")
    public QuestionGenerationResponse generateQuestions(@PathVariable Long resumeId) {

        return questionService.generateQuestions(resumeId);
    }
}