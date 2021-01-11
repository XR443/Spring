package com.github.hardlolmaster.module2.homework2.dao;

import com.github.hardlolmaster.module2.homework2.domain.Genre;
import org.springframework.data.repository.CrudRepository;

public interface IPersistenceManagerGenre extends CrudRepository<Genre, Long> {
    Genre findByName(String name);
}
