package in.sudhanshu.interview.question.dto;

import java.time.LocalDateTime;
import java.util.List;

public record QuestionSetResponse(
        Long id,
        LocalDateTime generatedAt,
        List<QuestionResponse> questions) {
}