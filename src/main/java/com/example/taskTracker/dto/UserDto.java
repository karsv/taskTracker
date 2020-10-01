package com.example.taskTracker.dto;

import com.example.taskTracker.validators.emailValidator.EmailValidator;
import com.example.taskTracker.validators.passwordEqualValidators.PasswordEqualValidator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordEqualValidator
public class UserDto {
    @NotNull(message = "User must have First Name!")
    @Size(min = 3, max = 21, message = "First name should be between 3 and 21")
    private String firstName;

    @NotNull(message = "User must have Last Name!")
    @Size(min = 3, max = 21, message = "Last name should be between 3 and 21")
    private String lastName;

    @EmailValidator
    @NotNull(message = "User must have email!")
    @Size(min = 3, max = 21, message = "Email should be between 3 and 21")
    private String email;

    @NotNull(message = "You must type password! Min = 3 symbols, max = 21")
    @Size(min = 3, max = 21, message = "Password should be between 3 and 21")
    private String password;

    @NotNull(message = "You must repeat typed password!")
    @Size(min = 3, max = 21, message = "Repeated password should be between 3 and 21")
    private String repeatedPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
