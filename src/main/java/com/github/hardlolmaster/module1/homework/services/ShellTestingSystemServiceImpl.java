package com.github.hardlolmaster.module1.homework.services;

import com.github.hardlolmaster.module1.homework.Properties;
import com.github.hardlolmaster.module1.homework.dao.IQuestionService;
import com.github.hardlolmaster.module1.homework.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Service(value = "shellLineTestSystem")
public class ShellTestingSystemServiceImpl implements ITestingSystemService {
    private final IQuestionService questionService;
    private final ITestingService testingService;
    private final MessageSource messageSource;
    private final Properties properties;
    private int currentQuestion = -1;

    @Autowired
    public ShellTestingSystemServiceImpl(IQuestionService questionService,
                                         ITestingService testingService,
                                         MessageSource messageSource,
                                         Properties properties) {
        this.questionService = questionService;
        this.testingService = testingService;
        this.messageSource = messageSource;
        this.properties = properties;
    }

    @Override
    public void startTesting() {
        questionService.regenerate();
        currentQuestion = 0;
    }

    @Override
    public void stopTesting() {
        if (isStopped()) return;
        currentQuestion = -1;
    }

    @Override
    public void getQuestion() {
        if (isStopped()) {
            System.out.println("Testing stopped");
            return;
        }
        Question question = question();
        if (question != null) {
            System.out.println(question.getNumber() + ". " +
                    "" + messageSource.getMessage("question." + question().getNumber(),
                    null,
                    new Locale(properties.getLocale())));
            for (int i = 0; i < question().getAnswers().size(); i++) {
                System.out.println((i + 1) + ". " + question().getAnswers().get(i));
            }
        } else {
            System.out.println("The questions are over");
        }
        System.out.println("Use 'stop' for end testing");
    }

    @Override
    public int answer(int number) {
        if (isStopped()) {
            return -2;
        }
        if (question() == null) {
            return -1;
        }
        requireNonNull(question()).setAnswer(number);
        currentQuestion += 1;
        return 0;
    }

    @Override
    public int getResult() {
        if (!isStopped()) {
            return -1;
        }
        return testingService.checkStudentAnswer(questionService.getQuestions());
    }

    private Question question() {
        List<Question> questions = questionService.getQuestions();
        if (questions != null && currentQuestion < questions.size())
            return questions.get(currentQuestion);
        return null;
    }

    @Override
    public boolean isStopped() {
        return currentQuestion == -1;
    }
}
