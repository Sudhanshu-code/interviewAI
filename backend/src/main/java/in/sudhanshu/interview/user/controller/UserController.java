package in.sudhanshu.interview.user.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sudhanshu.interview.user.entity.CurrentUserResponse;

@RestController
@RequestMapping("api/users")
public class UserController {

    @GetMapping("/me")
    public CurrentUserResponse me(Authentication authentication) {
        return new CurrentUserResponse(authentication.getName());
    }
}
