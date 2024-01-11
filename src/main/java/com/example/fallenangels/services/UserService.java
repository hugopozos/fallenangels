package com.example.fallenangels.services;

import com.example.fallenangels.models.User;
import com.example.fallenangels.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void getAllUsers()
    {
        userRepository.findAll();
    }
}
