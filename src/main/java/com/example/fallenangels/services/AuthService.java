package com.example.fallenangels.services;

import com.example.fallenangels.http.request.LoginRequest;
import com.example.fallenangels.http.request.RegisterRequest;
import com.example.fallenangels.http.response.AuthResponse;
import com.example.fallenangels.models.Role;
import com.example.fallenangels.models.Database1.User;
import com.example.fallenangels.repository.Database1.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Realiza el inicio de sesión y devuelve la respuesta de autenticación.
     *
     * @param request la solicitud de inicio de sesión
     * @return la respuesta de autenticación que contiene el token de acceso
     */
    public AuthResponse login(LoginRequest request) 
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        String token = jwtService.getToken(user);


        return AuthResponse.builder()
                .token(token)
                .build();
    }

    /**
     * Registra un nuevo usuario y devuelve la respuesta de autenticación.
     *
     * @param request la solicitud de registro del usuario
     * @return la respuesta de autenticación que contiene el token de acceso
     */
    public AuthResponse register(RegisterRequest request) 
    {
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

}
