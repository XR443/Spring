package com.github.hardlolmaster.repository;


import com.github.hardlolmaster.security.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}

