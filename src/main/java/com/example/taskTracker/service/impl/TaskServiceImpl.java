package com.example.taskTracker.service.impl;

import com.example.taskTracker.dao.TaskEntityRepository;
import com.example.taskTracker.dto.TaskDto;
import com.example.taskTracker.exceptions.TaskServiceException;
import com.example.taskTracker.model.Task;
import com.example.taskTracker.model.TaskStatus;
import com.example.taskTracker.service.TaskService;
import com.example.taskTracker.service.UserService;
import com.example.taskTracker.util.mapper.TaskMapper;
import java.util.Optional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

public class TaskServiceImpl implements TaskService {
    private final TaskEntityRepository taskEntityRepository;
    private final UserService userService;

    public TaskServiceImpl(TaskEntityRepository taskEntityRepository, UserService userService) {
        this.taskEntityRepository = taskEntityRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Task create(TaskDto taskCreateDto) {
        Task task = Mappers.getMapper(TaskMapper.class).taskDtoToTask(taskCreateDto);
        userService.addTask(taskCreateDto.getUserId(), task);
        return taskEntityRepository.save(task);
    }

    @Override
    public Task getById(Long id) {
        Optional<Task> task = taskEntityRepository.findById(id);
        if (task.isPresent()) {
            return task.get();
        }
        throw new TaskServiceException("No task with such id!");
    }

    @Override
    public Page<Task> getAllByStatus(Integer page, Integer positionsOnPage) {
        return taskEntityRepository
                .findAll(PageRequest.of(page, positionsOnPage, Sort.by("status")));
    }

    @Override
    public Page<Task> getAllByNewestUsers(Integer page, Integer positionsOnPage) {
        return taskEntityRepository
                .findAll(PageRequest.of(page, positionsOnPage, Sort.by("idOfCreator").ascending()));
    }

    @Override
    public Page<Task> getAllByOldestUsers(Integer page, Integer positionsOnPage) {
        return taskEntityRepository
                .findAll(PageRequest.of(page, positionsOnPage, Sort.by("idOfCreator").descending()));
    }

    @Override
    @Transactional
    public Task update(TaskDto taskDto) {
        Task task = getById(taskDto.getUserId());
        task.setStatus(TaskStatus.valueOf(taskDto.getStatus()));
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        return taskEntityRepository.save(task);
    }

    @Override
    public void delete(Long id) {
        taskEntityRepository.deleteById(id);
    }
}
