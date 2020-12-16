package com.github.hardlolmaster.module1.homework.services;

import com.github.hardlolmaster.module1.homework.domain.Question;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestingServiceImpl implements ITestingService {

    @Override
    public int checkStudentAnswer(List<Question> questions) {
        if (questions.isEmpty()) {
            return -1;
        }
        int result = 5;
        for (Question question : questions) {
            if (question.getAnswer() != question.getRightAnswer()) {
                result -= 1;
            }
        }
        return result;
    }
}
