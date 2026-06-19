package in.sudhanshu.interview.user.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sudhanshu.interview.user.entity.CurrentUserResponse;
import in.sudhanshu.interview.user.entity.User;
import in.sudhanshu.interview.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public CurrentUserResponse me(
            Authentication authentication) {

        User user = userRepository
                .findByEmail(
                        authentication.getName())
                .orElseThrow();

        return new CurrentUserResponse(
                user.getId(),
                user.getEmail(),
                user.getRole().name());
    }
}
