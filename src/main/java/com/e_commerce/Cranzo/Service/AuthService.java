package com.e_commerce.Cranzo.Service;

import com.e_commerce.Cranzo.dto.LoginRequest;
import com.e_commerce.Cranzo.dto.RegisterRequest;
import com.e_commerce.Cranzo.dto.AuthResponse;

public interface AuthService {
    void register(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest);
}
