package in.sudhanshu.interview.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import in.sudhanshu.interview.auth.dto.AuthResponse;
import in.sudhanshu.interview.auth.dto.LoginRequest;
import in.sudhanshu.interview.auth.dto.RegisterRequest;
import in.sudhanshu.interview.auth.service.AuthService;
import in.sudhanshu.interview.common.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);

        return new AuthResponse(true, "Registration successful");
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        String token = authService.login(request);
        cookieUtil.addAccessTokenCookies(response, token);

        return new AuthResponse(true, "Login successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        cookieUtil.clearAccessTokenCookie(response);
        return ResponseEntity.ok("Logged out successfully");
    }
}
