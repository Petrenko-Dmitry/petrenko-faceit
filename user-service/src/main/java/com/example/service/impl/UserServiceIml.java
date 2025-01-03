package com.example.service.impl;

import com.example.dto.AuthRequest;
import com.example.entity.TaskUser;
import com.example.repository.UserRepository;
import com.example.security.JWTUtil;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Service
public class UserServiceIml implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtil jwtTokenUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void register(AuthRequest user) {
        var existUser = this.userRepository.findByUsername(user.username());
        if (isNull(existUser)) {
            this.userRepository.save(
                    new TaskUser(
                            user.username(),
                            this.passwordEncoder.encode(
                                    user.password()
                            )
                    )
            );
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
    }

    @Override
    public String login(AuthRequest user) {
        Authentication authentication;
        try {
            authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.username(), user.password()));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The name or password is incorrect.", e);
        }
        var jwt = this.jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
        return jwt;
    }

    @Override
    public Map<String, String> validateToken(String token) {
        var response = new HashMap<String, String>();
        response.put("user", this.jwtTokenUtil.extractUsername(token));
        response.put("roles", this.jwtTokenUtil.extractAuthorities(token));
        return response;
    }
}
