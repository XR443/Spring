package com.github.hardlolmaster.module1.exercise.dao;

import com.github.hardlolmaster.module1.exercise.domain.Person;

public interface IPersonDao {
    Person findByName(String name);
}
