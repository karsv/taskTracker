package com.example.taskTracker.service;

import com.example.taskTracker.dto.TaskDto;
import com.example.taskTracker.dto.UserDto;
import com.example.taskTracker.dto.UserResponseDto;
import com.example.taskTracker.model.Task;
import com.example.taskTracker.model.User;
import org.springframework.data.domain.Page;

public interface UserService {
    User addTask(Long userId, Task task);

    User create(UserDto user);

    void delete(Long userId);

    User getUserByEmail(String email);

    UserResponseDto getUserResponseDtoById(Long userId);

    User update(Long userId, UserDto user);

    User updateTasks(Long userId, Task task);

    Page<UserResponseDto> getAllUsers(Integer page, Integer usersOnPage);
}
