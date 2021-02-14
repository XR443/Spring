package com.github.hardlolmaster.repository;

import com.github.hardlolmaster.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
