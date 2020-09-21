package com.example.taskTracker.service;

import com.example.taskTracker.dto.TaskDto;
import com.example.taskTracker.model.Task;
import com.example.taskTracker.model.TaskStatus;
import java.util.List;
import org.springframework.data.domain.Page;

public interface TaskService {
    Task create(Long userId, TaskDto taskDto);

    Task getById(Long id);

    Page<Task> getAllByStatus(Integer page, Integer positionsOnPage);

    Page<Task> getAllByNewestUsers(Integer page, Integer positionsOnPage);

    Page<Task> getAllByOldestUsers(Integer page, Integer positionsOnPage);

    Task update(Long taskId, Long userId, TaskDto taskDto);

    Task update(Long taskId, TaskStatus taskStatus);

    void delete(Long id);
}
