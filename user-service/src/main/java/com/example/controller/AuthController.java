package com.example.controller;

import com.example.dto.AuthRequest;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public void registerUser(@RequestBody AuthRequest user) {
        this.userService.register(user);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody AuthRequest user) {
        return this.userService.login(user);
    }

    @GetMapping("/validateToken")
    public Map<String, String> validateToken(@RequestParam("token") String token) {
        return this.userService.validateToken(token);
    }
}
