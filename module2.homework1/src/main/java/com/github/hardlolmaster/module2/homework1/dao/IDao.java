package com.github.hardlolmaster.module2.homework1.dao;

import java.util.List;

public interface IDao<T> {
    T findById(Long id);

    void insert(T obj);

    void update(T obj);

    void deleteById(Long id);

    List<T> findAll();
}
