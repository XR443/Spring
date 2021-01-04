package com.github.hardlolmaster.module1.homework.services;

import com.github.hardlolmaster.module1.homework.dao.ILoginService;
import com.github.hardlolmaster.module1.homework.dao.IQuestionService;
import com.github.hardlolmaster.module1.homework.domain.Question;
import com.github.hardlolmaster.module1.homework.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FilterInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineTestingSystemServiceImpl implements ITestingSystemService {

    private final ILoginService loginService;
    private final IQuestionService questionService;
    private final ITestingService testingService;
    private final IViewService viewService;

    @Autowired
    public CommandLineTestingSystemServiceImpl(ILoginService loginService,
                                               IQuestionService questionService,
                                               ITestingService testingService,
                                               IViewService viewService) {
        this.loginService = loginService;
        this.questionService = questionService;
        this.testingService = testingService;
        this.viewService = viewService;
    }

    @Override
    public void startTesting() {
        viewService.greetings();
        Scanner scanner = new Scanner(new FilterInputStream(System.in) {
            @Override
            public void close() throws IOException {
            }
        });
        viewService.enter("name");
        String name = scanner.next();
        viewService.enter("last name");
        String lastName = scanner.next();
        System.out.println();
        loginService.login(name, lastName);
        Student student = loginService.getCurrentStudent();

        List<Question> questions = questionService.regenerate();

        for (Question question : questions) {
            viewService.showQuestion(question);
            question.setAnswer(scanner.nextInt());
        }
        scanner.close();

        int mark = testingService.checkStudentAnswer(questions);
        viewService.showResults(student, mark);

        loginService.logout();
    }
}
