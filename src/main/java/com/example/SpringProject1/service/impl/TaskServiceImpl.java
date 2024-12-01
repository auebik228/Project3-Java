package com.example.SpringProject1.service.impl;

import com.example.SpringProject1.domain.exception.ResourceNotFoundException;
import com.example.SpringProject1.domain.task.Status;
import com.example.SpringProject1.domain.task.Task;
import com.example.SpringProject1.repository.TaskRepository;
import com.example.SpringProject1.repository.UserRepository;
import com.example.SpringProject1.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public Task getById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    @Transactional
    @Override
    public List<Task> findAllByUserId(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Transactional
    @Override
    public Task update(Task task) {
        if (task.getStatus() == null) {
            task.setStatus(Status.TODO);
        }
        taskRepository.update(task);
        return task;
    }

    @Transactional
    @Override
    public Task create(Task task, Long id) {
        task.setStatus(Status.TODO);
        taskRepository.create(task);
        taskRepository.assignToUserById(task.getId(), id);
        return task;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        taskRepository.delete(id);
    }
}




