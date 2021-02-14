package com.github.hardlolmaster.repository;

import com.github.hardlolmaster.domain.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
}
