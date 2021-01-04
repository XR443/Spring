package com.github.hardlolmaster.module1.homework.dao;

import com.github.hardlolmaster.module1.homework.domain.Student;

public interface ILoginService {

    int login(String name, String lastName);

    void logout();

    Student getCurrentStudent();
}
