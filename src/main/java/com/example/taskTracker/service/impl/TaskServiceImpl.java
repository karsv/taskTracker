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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskEntityRepository taskEntityRepository;
    private final UserService userService;

    public TaskServiceImpl(TaskEntityRepository taskEntityRepository, UserService userService) {
        this.taskEntityRepository = taskEntityRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Task create(Long userId, TaskDto taskDto) {
        try {
            Task task = Mappers.getMapper(TaskMapper.class).taskDtoToTask(taskDto);
            task.setUserId(userId);
            Task savedTask = taskEntityRepository.save(task);
            userService.addTask(userId, savedTask);
            return savedTask;
        } catch (IllegalArgumentException e){
            throw new TaskServiceException("No such status for task!");
        }
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
    public Task update(Long taskId, Long userId, TaskDto taskDto) {
        Task task = getById(taskId);
        task.setUserId(userId);
        task.setStatus(TaskStatus.valueOf(taskDto.getStatus()));
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        return taskEntityRepository.save(task);
    }

    @Override
    public Task update(Long taskId, TaskStatus taskStatus) {
        Task task = getById(taskId);
        task.setStatus(taskStatus);
        return taskEntityRepository.save(task);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Task task = taskEntityRepository.findById(id)
                .orElseThrow(() -> new TaskServiceException("No task with such id!"));
        userService.updateTasks(task.getUserId(), task);
        taskEntityRepository.deleteById(id);
    }
}
