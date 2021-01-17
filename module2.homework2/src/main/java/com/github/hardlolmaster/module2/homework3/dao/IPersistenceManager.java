package com.github.hardlolmaster.module2.homework3.dao;

public interface IPersistenceManager<PersistentObject> {
    void save(PersistentObject object);
    PersistentObject getByID(Long id);
}
