package com.example.service;

import com.example.dto.TaskDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TaskService {
    void createTask(TaskDTO taskDTO, List<MultipartFile> files) throws IOException;
    TaskDTO getTask(Long id);
    void updateTask(Long id, TaskDTO taskDTO);
    void deleteTask(Long id);
}
