package com.example.SpringProject1.web.controller;

import com.example.SpringProject1.domain.task.Task;
import com.example.SpringProject1.domain.user.User;
import com.example.SpringProject1.repository.UserRepository;
import com.example.SpringProject1.service.TaskService;
import com.example.SpringProject1.service.UserService;
import com.example.SpringProject1.web.dto.task.TaskDto;
import com.example.SpringProject1.web.dto.user.UserDto;
import com.example.SpringProject1.web.dto.validation.OnCreate;
import com.example.SpringProject1.web.dto.validation.OnUpdate;
import com.example.SpringProject1.web.mappers.TaskMapper;
import com.example.SpringProject1.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final TaskService taskService;

    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @PutMapping
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto dto) {
        User user = userMapper.toEntity(dto);
        User userUpdated = userService.update(user);
        return userMapper.toDto(userUpdated);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}/tasks")
    public List<TaskDto> getTasksByUserId(@PathVariable Long id) {
        List<Task> tasks = taskService.findAllByUserId(id);
        return taskMapper.toDto(tasks);
    }

    @PostMapping("/{id}/tasks")
    public TaskDto createTask(@PathVariable Long id, @Validated(OnCreate.class) @RequestBody TaskDto dto) {
        Task task = taskMapper.toEntity(dto);
        Task createdTask = taskService.create(task, id);
        return taskMapper.toDto(createdTask);
    }


}
