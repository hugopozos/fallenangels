package com.example.fallenangels.services;

import com.example.fallenangels.http.request.LoginRequest;
import com.example.fallenangels.http.request.RegisterRequest;
import com.example.fallenangels.http.response.AuthResponse;
import com.example.fallenangels.models.User;
import com.example.fallenangels.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    public AuthResponse login(LoginRequest request) {
        return null;
    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(request.getPassword())
                .build();
        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

}
