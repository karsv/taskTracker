package com.example.taskTracker.validators.emailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.EmailValidator;

public class EmailCustomValidator implements ConstraintValidator<com.example.taskTracker.validators.emailValidator.EmailValidator, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return EmailValidator.getInstance().isValid(s);
    }
}
