package com.github.hardlolmaster.module1.homework.services;

import com.github.hardlolmaster.module1.homework.domain.Question;
import com.github.hardlolmaster.module1.homework.domain.Student;

public interface IViewService {
    void greetings();

    void enter(String whatEnter);

    void showQuestion(Question question);

    void showResults(Student student, int mark);
}
