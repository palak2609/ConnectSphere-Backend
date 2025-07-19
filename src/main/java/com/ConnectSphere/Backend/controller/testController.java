package com.ConnectSphere.Backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @GetMapping("/")
    public String home() {
        return "Spring Boot is Working Fine!";
    }
}
