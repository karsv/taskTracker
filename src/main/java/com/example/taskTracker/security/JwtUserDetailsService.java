package com.example.taskTracker.security;

import com.example.taskTracker.exceptions.AuthenticationException;
import com.example.taskTracker.service.UserService;
import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class JwtUserDetailsService implements UserDetailsService {
	private final UserService userService;

	public JwtUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.example.taskTracker.model.User userByEmail = userService.getUserByEmail(email);
		if (userByEmail.getEmail().equals(email)) {
			return new User(userByEmail.getEmail(),
					userByEmail.getPassword(),
					new ArrayList<>());
		} else {
			throw new AuthenticationException("User not found with email: " + email);
		}
	}
}