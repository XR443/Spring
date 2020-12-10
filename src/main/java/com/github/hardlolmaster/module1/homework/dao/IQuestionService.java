package com.github.hardlolmaster.module1.homework.dao;

import com.github.hardlolmaster.module1.homework.domain.Question;

import java.util.List;

public interface IQuestionService {

    List<Question> regenerate();
    List<Question> getQuestions();
}
