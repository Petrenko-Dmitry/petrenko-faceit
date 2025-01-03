package com.example.service.impl;

import com.example.dto.TaskDTO;
import com.example.entity.AttachedFile;
import com.example.entity.Task;
import com.example.repository.TaskRepository;
import com.example.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void createTask(TaskDTO taskDTO, List<MultipartFile> files) throws IOException {
        var task = new Task(taskDTO);
        if (nonNull(files) && !files.isEmpty()) {
            for (MultipartFile file : files) {
                var attachedFile = new AttachedFile(file, task);
                task.getAttachedFiles().add(attachedFile);
            }
        }
        this.taskRepository.save(task);
    }

    @Override
    public TaskDTO getTask(Long id) {
        var task = this.taskRepository.findById(id);
        return new TaskDTO(task.orElseGet(Task::new));
    }

    @Override
    public void updateTask(Long id, TaskDTO taskDTO) {
        var task = this.taskRepository.findById(id);
        if (task.isPresent()) {
            var updateTask = task.get().update(taskDTO);
            this.taskRepository.save(updateTask);
        } else {
            throw new RuntimeException("Not found task by Id: " + id);
        }
    }

    @Override
    public void deleteTask(Long id) {
        this.taskRepository.deleteById(id);
    }
}
