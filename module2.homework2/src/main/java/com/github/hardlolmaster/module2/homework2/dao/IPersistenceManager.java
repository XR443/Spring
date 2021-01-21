package com.github.hardlolmaster.module2.homework2.dao;

import java.util.List;

public interface IPersistenceManager<PersistentObject> {
    void save(PersistentObject object);
    PersistentObject getByID(Long id);
    List<PersistentObject> findAll();
}
