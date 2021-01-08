package com.github.hardlolmaster.module2.homework2.dao;

import com.github.hardlolmaster.module2.homework2.domain.Genre;

public interface IPersistenceManager<PersistentObject> {
    void save(PersistentObject object);
    PersistentObject getByID(Long id);
}
