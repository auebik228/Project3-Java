package com.example.SpringProject1.repository;

import com.example.SpringProject1.domain.task.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Optional<Task> findById(long id);

    List<Task> findAllByUserId(Long userId);

    void assignToUserById(Long taskId, Long userId);

    void update(Task task);

    void create(Task task);

    void delete(Long id);

}
