package com.example.taskTracker.validators.emailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailCustomValidator.class)
public @interface EmailValidator {
    String message() default "Email doesn't meet conditions!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
