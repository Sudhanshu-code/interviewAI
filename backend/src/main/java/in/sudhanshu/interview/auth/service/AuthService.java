package in.sudhanshu.interview.auth.service;

import in.sudhanshu.interview.auth.dto.*;

public interface AuthService {

    void register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}