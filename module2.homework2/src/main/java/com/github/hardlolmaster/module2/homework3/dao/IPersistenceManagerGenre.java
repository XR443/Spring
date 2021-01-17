package com.github.hardlolmaster.module2.homework3.dao;

import com.github.hardlolmaster.module2.homework3.domain.Genre;

public interface IPersistenceManagerGenre extends IPersistenceManager<Genre> {
    Genre getByName(String name);
}
