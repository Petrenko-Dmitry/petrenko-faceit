package com.example.service;

import com.example.dto.AuthRequest;
import com.example.dto.UserAuthority;

public interface UserService {
    void register(AuthRequest user);
    String login(AuthRequest user);
    UserAuthority validateToken(String token);
}
