package com.example.taskTracker.controllers;

import com.example.taskTracker.dto.UserDto;
import com.example.taskTracker.model.User;
import com.example.taskTracker.security.JwtRequest;
import com.example.taskTracker.security.JwtResponse;
import com.example.taskTracker.security.JwtUserDetailsService;
import com.example.taskTracker.service.UserService;
import com.example.taskTracker.util.JwtTokenUtil;
import javax.validation.Valid;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@ApiOperation(value = "authenticate")
public class AuthenticateController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticateController(UserService userService,
                                  AuthenticationManager authenticationManager,
                                  JwtTokenUtil jwtTokenUtil,
                                  JwtUserDetailsService userDetailsService,
                                  PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/registration")
    public ResponseEntity<User> register(@RequestBody @Valid UserDto userRegistrationDto) {
        try {
            String encodedPassword = passwordEncoder.encode(userRegistrationDto.getPassword());
            userRegistrationDto.setPassword(encodedPassword);
            userRegistrationDto.setRepeatedPassword(encodedPassword);
            User user = userService.create(userRegistrationDto);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity("There is user with such email!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
            throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        UserDetails userDetails = userDetailsService.
                loadUserByUsername(authenticationRequest.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity validationError(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
    }
}
