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

    void delete(Long id);

    UserResponseDto getUserResponseDtoById(Long id);

    User update(Long id, UserDto user);

    Page<UserResponseDto> getAllUsers(Integer page, Integer usersOnPage);
}
