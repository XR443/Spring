package com.github.hardlolmaster.module1.exercise.dao;

import com.github.hardlolmaster.module1.exercise.domain.Person;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDaoSimple implements IPersonDao {
    private int defaultAge = 18;

    @Override
    public Person findByName(String name) {
        return new Person(name, defaultAge);
    }

    public void setDefaultAge(int defaultAge) {
        this.defaultAge = defaultAge;
    }
}
