package com.example.service;

import com.example.dto.AuthRequest;

import java.util.Map;

public interface UserService {
    void register(AuthRequest user);
    String login(AuthRequest user);
    Map<String, String> validateToken(String token);
}
