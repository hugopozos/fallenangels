package com.example.fallenangels.http.controllers;

import com.example.fallenangels.http.request.LoginRequest;
import com.example.fallenangels.http.request.RegisterRequest;
import com.example.fallenangels.http.response.AuthResponse;
import com.example.fallenangels.models.User;
import com.example.fallenangels.repository.UserRepository;
import com.example.fallenangels.services.AuthService;
import com.example.fallenangels.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }

}
