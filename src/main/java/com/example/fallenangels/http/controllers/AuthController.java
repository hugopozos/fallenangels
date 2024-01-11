package com.example.fallenangels.http.controllers;

import com.example.fallenangels.http.request.LoginRequest;
import com.example.fallenangels.http.request.RegisterRequest;
import com.example.fallenangels.http.response.AuthResponse;
import com.example.fallenangels.services.AuthService;
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

    /**
     * Realiza el inicio de sesión para un usuario.
     *
     * @param request la solicitud de inicio de sesión que contiene las credenciales del usuario
     * @return una respuesta HTTP con los detalles de autenticación del usuario
     */
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param request la solicitud de registro del usuario
     * @return la respuesta de autenticación
     */
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }

}
