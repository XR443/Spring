package com.github.hardlolmaster.web;

import com.github.hardlolmaster.domain.Post;
import com.github.hardlolmaster.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PostController {
    private final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/post")
    public Flux<Post> posts() {
        return postRepository.findAll();
    }

    @PostMapping("/post/create")
    public Mono<Void> create(Post post) {
        return postRepository.save(post).then();
    }

    @PutMapping("/post/update")
    public Mono<Void> update(Post post) {
        return postRepository.save(post).then();
    }

    @DeleteMapping("/post/delete")
    public Mono<Void> delete(Post user) {
        return postRepository.delete(user).then();
    }

    @DeleteMapping("/post/deleteAll")
    public Mono<Void> deleteAll() {
        return postRepository.deleteAll().then();
    }
}
