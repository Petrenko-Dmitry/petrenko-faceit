package com.example.entity;

import com.example.dto.TaskDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private long userId;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AttachedFile> attachedFiles;

    public Task(TaskDTO taskDTO) {
        this.title = taskDTO.title();
        this.description = taskDTO.description();
        this.dueDate = taskDTO.dueDate();
        this.status = taskDTO.status();
        this.userId = taskDTO.userId();
        this.attachedFiles = new ArrayList<>();
    }

    public Task update(TaskDTO taskDTO) {
        this.title = taskDTO.title();
        this.description = taskDTO.description();
        this.dueDate = taskDTO.dueDate();
        this.status = taskDTO.status();
        this.userId = taskDTO.userId();
        return this;
    }
}
