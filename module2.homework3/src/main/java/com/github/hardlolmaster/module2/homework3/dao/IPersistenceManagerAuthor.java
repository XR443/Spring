package com.github.hardlolmaster.module2.homework3.dao;

import com.github.hardlolmaster.module2.homework3.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface IPersistenceManagerAuthor extends CrudRepository<Author, Long> {
    Author findByNameAndLastName(String name, String lastName);
}
