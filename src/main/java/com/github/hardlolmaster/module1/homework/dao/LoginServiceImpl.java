package com.github.hardlolmaster.module1.homework.dao;

import com.github.hardlolmaster.module1.homework.domain.Student;
import org.springframework.stereotype.Repository;

@Repository
public class LoginServiceImpl implements ILoginService {

    private Student currentStudent;

    @Override
    public void login(String name, String lastName) {
        if (currentStudent != null) {
            System.out.println("LOGOUT FIRST");
            return;
        }
        currentStudent = new Student(name, lastName);
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
