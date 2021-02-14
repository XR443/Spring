package com.github.hardlolmaster.web;

import com.github.hardlolmaster.domain.Comment;
import com.github.hardlolmaster.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CommentController {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping("/comment")
    public Flux<Comment> comments() {
        return commentRepository.findAll();
    }

    @PostMapping("/comment/create")
    public Mono<Void> create(Comment comment) {
        return commentRepository.save(comment).then();
    }

    @PutMapping("/comment/update")
    public Mono<Void> update(Comment comment) {
        return commentRepository.save(comment).then();
    }

    @DeleteMapping("/comment/delete")
    public Mono<Void> delete(Comment user) {
        return commentRepository.delete(user).then();
    }

    @DeleteMapping("/comment/deleteAll")
    public Mono<Void> deleteAll() {
        return commentRepository.deleteAll().then();
    }
}
