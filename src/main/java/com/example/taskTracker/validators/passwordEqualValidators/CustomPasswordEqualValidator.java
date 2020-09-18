package com.example.taskTracker.validators.passwordEqualValidators;

import com.example.taskTracker.dto.UserDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomPasswordEqualValidator implements ConstraintValidator<PasswordEqualValidator, UserDto> {

    @Override
    public boolean isValid(UserDto userRegistrationDto, ConstraintValidatorContext constraintValidatorContext) {
        return userRegistrationDto.getPassword().equals(userRegistrationDto.getRepeatedPassword());
    }
}
