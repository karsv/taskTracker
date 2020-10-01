package com.example.taskTracker.validators.passwordEqualValidators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomPasswordEqualValidator.class)
@Target(ElementType.TYPE)
public @interface PasswordEqualValidator {
    String message() default "Passwords aren't equal!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
