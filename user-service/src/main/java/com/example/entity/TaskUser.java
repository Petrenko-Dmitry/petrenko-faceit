package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.entity.Role.USER;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Role role;

    public TaskUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = USER;
    }
}

