package in.sudhanshu.interview.ai.dto;

import in.sudhanshu.interview.question.entity.Category;
import in.sudhanshu.interview.question.entity.Difficulty;

public record GeneratedQuestion(String question, Difficulty difficulty, Category category) {
}