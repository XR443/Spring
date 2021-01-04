package com.github.hardlolmaster.module1.homework14.services;

import com.github.hardlolmaster.module1.homework14.domain.Question;

import java.util.List;

public interface ITestingService {
    int checkStudentAnswer(List<Question> questions);
}
