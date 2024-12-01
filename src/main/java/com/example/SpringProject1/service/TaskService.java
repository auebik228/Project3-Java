package com.example.SpringProject1.service;

import com.example.SpringProject1.domain.task.Task;

import java.util.List;

public interface TaskService {
    Task getById(long id);

    List<Task> findAllByUserId(Long userId);

    Task update(Task task);

    Task create(Task task, Long id);

    void delete(Long id);
}
