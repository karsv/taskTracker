package com.example.taskTracker.service.impl;

import com.example.taskTracker.dao.UserEntityRepository;
import com.example.taskTracker.dto.TaskDto;
import com.example.taskTracker.dto.UserDto;
import com.example.taskTracker.dto.UserResponseDto;
import com.example.taskTracker.exceptions.UserServiceException;
import com.example.taskTracker.model.Task;
import com.example.taskTracker.model.User;
import com.example.taskTracker.service.UserService;
import com.example.taskTracker.util.mapper.TaskMapper;
import com.example.taskTracker.util.mapper.UserMapper;
import javax.validation.Valid;
import java.util.Optional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserEntityRepository userEntityRepository;

    public UserServiceImpl(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    @Transactional
    public User addTask(Long userId, Task task) {
        Optional<User> userOptional = userEntityRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserServiceException("No such user!");
        }
        User user = userOptional.get();
        user.addTask(task);
        return userEntityRepository.save(user);
    }

    @Override
    public User create(@Valid UserDto userDto) {
        User user = Mappers.getMapper(UserMapper.class).
                userDtoToUser(userDto);
        User savedUser = userEntityRepository.save(user);
        return savedUser;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userEntityRepository.findById(id)
                .orElseThrow(() -> new UserServiceException("No such user!"));
        userEntityRepository.deleteById(id);
    }

    @Override
    public UserResponseDto getUserResponseDtoById(Long id) {
        return userEntityRepository
                .findById(id)
                .map(user -> Mappers.getMapper(UserMapper.class).userToUserResponseDto(user))
                .orElseThrow(() -> new UserServiceException("No such user!"));
    }

    @Override
    @Transactional
    public User update(Long id, UserDto user) {
        return userEntityRepository.findById(id).map(updatedUser -> {
            updatedUser.setEmail(user.getEmail());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setPassword(user.getPassword());
            return userEntityRepository.save(updatedUser);
        }).orElseThrow(() -> new UserServiceException("No such user!"));
    }

    @Override
    public Page<UserResponseDto> getAllUsers(Integer page, Integer usersOnPage) {
        return userEntityRepository
                .findAll(PageRequest.of(page, usersOnPage))
                .map(user -> Mappers.getMapper(UserMapper.class).userToUserResponseDto(user));
    }
}
