package com.github.hardlolmaster.module2.homework2.dao;

import com.github.hardlolmaster.module2.homework2.domain.Comment;
import org.springframework.data.repository.CrudRepository;

public interface IPersistenceManagerComment extends CrudRepository<Comment, Long> {
}
