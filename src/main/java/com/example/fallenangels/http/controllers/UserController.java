package com.example.fallenangels.http.controllers;

import com.example.fallenangels.http.request.RegisterRequest;
import com.example.fallenangels.http.response.UserResponse;
import com.example.fallenangels.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id)
    {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody RegisterRequest request)
    {
        UserResponse user = userService.createUser(request);
        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Integer id, @RequestBody RegisterRequest request)
    {
        UserResponse user = userService.updateUser(id, request);
        return ResponseEntity.ok(user);
    }


}
