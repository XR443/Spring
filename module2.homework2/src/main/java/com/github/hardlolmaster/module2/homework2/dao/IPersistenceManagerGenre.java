package com.github.hardlolmaster.module2.homework2.dao;

import com.github.hardlolmaster.module2.homework2.domain.Genre;

public interface IPersistenceManagerGenre extends IPersistenceManager<Genre> {
    Genre getByName(String name);
}
