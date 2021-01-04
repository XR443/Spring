package com.github.hardlolmaster.module1.homework14.services;

import com.github.hardlolmaster.module1.homework14.domain.Question;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TestingServiceImpl implements ITestingService {

    @Override
    public int checkStudentAnswer(List<Question> questions) {
        if (questions.isEmpty()) return -1;
        int result = 5;
        for (Question question : questions) {
            if (question.getAnswer() != question.getRightAnswer()) result -= 1;
        }
        return result;
    }
}
