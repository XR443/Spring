package com.github.hardlolmaster.web;

import com.github.hardlolmaster.repository.UserRepository;
import com.github.hardlolmaster.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public Flux<User> users() {
        return userRepository.findAll();
    }

    @PostMapping("/user/create")
    public Mono<Void> create(User user) {
        return userRepository.save(user).then();
    }

    @PutMapping("/user/update")
    public Mono<Void> update(User user) {
        return userRepository.save(user).then();
    }

    @DeleteMapping("/user/delete")
    public Mono<Void> delete(User user) {
        return userRepository.delete(user).then();
    }

    @DeleteMapping("/user/deleteAll")
    public Mono<Void> deleteAll() {
        return userRepository.deleteAll().then();
    }

}
