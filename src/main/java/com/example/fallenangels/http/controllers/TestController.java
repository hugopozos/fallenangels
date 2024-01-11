package com.example.fallenangels.http.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    @PostMapping(value = "test")
    public String test()
    {
        return "Hola mundo";
    }
}
