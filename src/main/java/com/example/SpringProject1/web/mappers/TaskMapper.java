package com.example.SpringProject1.web.mappers;

import com.example.SpringProject1.domain.task.Task;
import com.example.SpringProject1.web.dto.task.TaskDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task task);
    List<TaskDto> toDto(List<Task> tasks);
    Task toEntity(TaskDto taskDto);
}
