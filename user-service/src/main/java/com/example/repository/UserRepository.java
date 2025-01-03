package com.example.repository;

import com.example.entity.TaskUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<TaskUser, Long> {
    TaskUser findByUsername(String username);
}
