package com.github.hardlolmaster.module2.homework3.dao;

import com.github.hardlolmaster.module2.homework3.domain.Author;

public interface IPersistenceManagerAuthor extends IPersistenceManager<Author> {
    Author getByName(String name, String lastName);
}
