package in.sudhanshu.interview.user.entity;

public record CurrentUserResponse(
        Long id,
        String email,
        String role) {
}
