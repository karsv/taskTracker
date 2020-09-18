package com.example.taskTracker.dao;

import com.example.taskTracker.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserEntityRepository extends PagingAndSortingRepository<User, Long> {

}
