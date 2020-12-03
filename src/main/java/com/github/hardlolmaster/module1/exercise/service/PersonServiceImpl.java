package com.github.hardlolmaster.module1.exercise.service;

import com.github.hardlolmaster.module1.exercise.dao.IPersonDao;
import com.github.hardlolmaster.module1.exercise.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements IPersonService {

    private IPersonDao dao;

    public PersonServiceImpl() {
    }

    @Autowired
    public PersonServiceImpl(IPersonDao dao) {
        this.dao = dao;
    }

    @Override
    public Person getByName(String name) {
        return dao.findByName(name);
    }

    public IPersonDao getDao() {
        return dao;
    }

    public void setDao(IPersonDao dao) {
        this.dao = dao;
    }
}
