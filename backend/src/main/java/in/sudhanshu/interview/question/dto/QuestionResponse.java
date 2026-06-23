package in.sudhanshu.interview.question.dto;

public record QuestionResponse(
        Long id,
        String questionText,
        String difficulty,
        String category) {
}