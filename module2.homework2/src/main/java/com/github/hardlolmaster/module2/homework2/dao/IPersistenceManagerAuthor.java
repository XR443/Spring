package com.github.hardlolmaster.module2.homework2.dao;

import com.github.hardlolmaster.module2.homework2.domain.Author;

public interface IPersistenceManagerAuthor extends IPersistenceManager<Author> {
    Author getByName(String name, String lastName);
}
