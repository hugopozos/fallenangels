package com.example.fallenangels.services;

import com.example.fallenangels.http.request.RegisterRequest;
import com.example.fallenangels.http.response.UserResponse;
import com.example.fallenangels.models.Role;
import com.example.fallenangels.models.User;
import com.example.fallenangels.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public List<UserResponse> getAllUsers()
    {
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .createdAt(String.valueOf(user.getCreatedAt()))
                        .updatedAt(String.valueOf(user.getUpdatedAt()))
                        .build())
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Integer id)
    {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .createdAt(String.valueOf(user.getCreatedAt()))
                .updatedAt(String.valueOf(user.getUpdatedAt()))
                .build();
    }

    public UserResponse createUser(RegisterRequest request)
    {
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .createdAt(String.valueOf(user.getCreatedAt()))
                .updatedAt(String.valueOf(user.getUpdatedAt()))
                .build();
    }


    public UserResponse updateUser(Integer id, RegisterRequest request)
    {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        user.setName(request.getName());
        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .createdAt(String.valueOf(user.getCreatedAt()))
                .updatedAt(String.valueOf(user.getUpdatedAt()))
                .build();
    }
}
