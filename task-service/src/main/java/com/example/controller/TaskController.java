package com.example.controller;

import com.example.dto.TaskDTO;
import com.example.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping(value = "/createTask")
    public void createTask(
            @RequestPart("task") TaskDTO task,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws IOException {
        this.taskService.createTask(task, files);
    }

    @GetMapping(value = "/get")
    public TaskDTO getTask(
            @RequestParam("id") Long id
    ) {
        return this.taskService.getTask(id);
    }

    @PostMapping(value = "/update/{id}")
    private void updateTask(
            @PathVariable("id") Long id,
            @RequestBody TaskDTO task
    ) {
        this.taskService.updateTask(id, task);
    }

    @PostMapping(value = "/delete/{id}")
    public void deleteTask(
            @PathVariable("id") Long id
    ) {
        this.taskService.deleteTask(id);
    }
}

