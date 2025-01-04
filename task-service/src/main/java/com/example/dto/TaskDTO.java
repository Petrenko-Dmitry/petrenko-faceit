package com.example.dto;

import com.example.entity.Task;
import com.example.entity.TaskStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.dto.AttachedFileDTO.convertToDTO;
import static org.springframework.util.CollectionUtils.isEmpty;

public record TaskDTO(
        Long id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskStatus status,
        long userId,
        List<AttachedFileDTO> attachedFiles
) {

    public TaskDTO(Task task) {
        this(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus(),
                task.getUserId(),
                isEmpty(task.getAttachedFiles()) ? new ArrayList<>() : convertToDTO(task.getAttachedFiles())
        );
    }
}
