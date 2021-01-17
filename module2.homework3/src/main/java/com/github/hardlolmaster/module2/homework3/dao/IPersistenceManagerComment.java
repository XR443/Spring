package com.github.hardlolmaster.module2.homework3.dao;

import com.github.hardlolmaster.module2.homework3.domain.Comment;
import org.springframework.data.repository.CrudRepository;

public interface IPersistenceManagerComment extends CrudRepository<Comment, Long> {
}
