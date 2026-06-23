package in.sudhanshu.interview.ai.dto;

import java.util.List;

public record GeminiQuestionResponse(List<GeneratedQuestion> questions) {
}