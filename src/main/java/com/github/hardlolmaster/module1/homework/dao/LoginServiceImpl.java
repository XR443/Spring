package com.github.hardlolmaster.module1.homework.dao;

import com.github.hardlolmaster.module1.homework.domain.Student;
import org.springframework.stereotype.Repository;

@Repository
public class LoginServiceImpl implements ILoginService {

    private Student currentStudent;

    @Override
    public int login(String name, String lastName) {
        if (currentStudent != null) {
            return -1;
        }
        currentStudent = new Student(name, lastName);
        return 0;
    }

    @Override
    public void logout() {
        currentStudent = null;
    }

    @Override
    public Student getCurrentStudent() {
        return currentStudent;
    }

}
