package com.github.hardlolmaster.module1.homework.services;

import com.github.hardlolmaster.module1.homework.domain.Question;
import com.github.hardlolmaster.module1.homework.domain.Student;

import java.util.List;

public interface ITestingService {
    int checkStudentAnswer(List<Question> questions);
}
