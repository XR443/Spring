package com.github.hardlolmaster.module2.homework4.dao;

import com.github.hardlolmaster.module2.homework4.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface CommentRepository extends MongoRepository<Comment, BigInteger> {
}
