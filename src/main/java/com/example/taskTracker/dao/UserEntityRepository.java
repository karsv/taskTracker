package com.example.taskTracker.dao;

import com.example.taskTracker.model.User;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserEntityRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByEmail(String email);

}
