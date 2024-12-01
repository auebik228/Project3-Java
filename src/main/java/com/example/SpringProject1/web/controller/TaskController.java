package com.example.SpringProject1.web.controller;

import com.example.SpringProject1.domain.task.Task;
import com.example.SpringProject1.service.TaskService;
import com.example.SpringProject1.web.dto.task.TaskDto;
import com.example.SpringProject1.web.dto.validation.OnUpdate;
import com.example.SpringProject1.web.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {
    private final TaskService taskService;

    private final TaskMapper taskMapper;

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        taskService.delete(id);
    }
    @PutMapping
    public TaskDto update(@RequestBody @Validated(OnUpdate.class) TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        Task updatedTask = taskService.update(task);
        return taskMapper.toDto(updatedTask);
    }
}
