package com.github.hardlolmaster.repository;

import com.github.hardlolmaster.domain.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    @Query("{ 'username' : '?0' }")
    Mono<User> findByUsername(String username);
}

