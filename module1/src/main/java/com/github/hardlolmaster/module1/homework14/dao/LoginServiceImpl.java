package com.github.hardlolmaster.module1.homework14.dao;

import com.github.hardlolmaster.module1.homework14.domain.Student;
import org.springframework.stereotype.Repository;

@Repository
public class LoginServiceImpl implements ILoginService {

    @Override
    public Student login(String name, String lastName) {
        return new Student(name, lastName);
    }

}
