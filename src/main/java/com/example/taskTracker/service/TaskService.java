package com.example.taskTracker.service;

import com.example.taskTracker.dto.TaskDto;
import com.example.taskTracker.model.Task;
import java.util.List;
import org.springframework.data.domain.Page;

public interface TaskService {
    Task create(TaskDto taskCreateDto);

    Task getById(Long id);

    Page<Task> getAllByStatus(Integer page, Integer positionsOnPage);

    Page<Task> getAllByNewestUsers(Integer page, Integer positionsOnPage);

    Page<Task> getAllByOldestUsers(Integer page, Integer positionsOnPage);

    Task update(TaskDto taskUpdateDto);

    void delete(Long id);
}
