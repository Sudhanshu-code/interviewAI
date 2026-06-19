package in.sudhanshu.interview.user.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.sudhanshu.interview.auth.dto.AuthResponse;
import in.sudhanshu.interview.auth.dto.LoginRequest;
import in.sudhanshu.interview.auth.dto.RegisterRequest;
import in.sudhanshu.interview.auth.service.AuthService;
import in.sudhanshu.interview.exception.EmailAlreadyExistsException;
import in.sudhanshu.interview.security.JwtService;
import in.sudhanshu.interview.user.entity.Role;
import in.sudhanshu.interview.user.entity.User;
import in.sudhanshu.interview.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("User with this email already exits");
        }

        User user = User.builder()
                .name(request.name())
                .role(Role.USER)
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    @Override
    public String login(LoginRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        User user = userRepository.findByEmail(request.email()).orElseThrow();
        String token = jwtService.generateToken(user);
        return token;
    }

}
