package com.github.hardlolmaster.repository;

import com.github.hardlolmaster.domain.Post;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PostRepository extends ReactiveMongoRepository<Post, String> {
}
