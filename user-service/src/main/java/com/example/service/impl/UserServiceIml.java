package com.example.service.impl;

import com.example.dto.AuthRequest;
import com.example.dto.UserAuthority;
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
        return this.jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
    }

    @Override
    public UserAuthority validateToken(String token) {
        return new UserAuthority(
                this.jwtTokenUtil.extractUsername(token),
                this.jwtTokenUtil.extractAuthorities(token)
        );
    }
}
