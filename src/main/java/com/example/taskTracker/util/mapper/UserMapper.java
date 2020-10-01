package com.example.taskTracker.util.mapper;

import com.example.taskTracker.dto.UserDto;
import com.example.taskTracker.dto.UserResponseDto;
import com.example.taskTracker.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User userDtoToUser(UserDto userDto);

    UserResponseDto userToUserResponseDto(User user);
}
