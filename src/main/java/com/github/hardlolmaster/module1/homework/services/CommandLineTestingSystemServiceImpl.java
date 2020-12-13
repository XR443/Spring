package com.github.hardlolmaster.module1.homework.services;

import com.github.hardlolmaster.module1.homework.dao.ILoginService;
import com.github.hardlolmaster.module1.homework.dao.IQuestionService;
import com.github.hardlolmaster.module1.homework.domain.Question;
import com.github.hardlolmaster.module1.homework.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Service(value = "commandLineTestSystem")
public class CommandLineTestingSystemServiceImpl implements ITestingSystemService {

    private ILoginService loginService;
    private IQuestionService questionService;
    private ITestingService testingService;
    private MessageSource messageSource;

    @Autowired
    public CommandLineTestingSystemServiceImpl(ILoginService loginService,
                                               IQuestionService questionService,
                                               ITestingService testingService,
                                               MessageSource messageSource) {
        this.loginService = loginService;
        this.questionService = questionService;
        this.testingService = testingService;
        this.messageSource = messageSource;
    }

    @Override
    public void startTesting() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.next();
        System.out.print("Enter your last name: ");
        String lastName = scanner.next();
        System.out.println();
        loginService.login(name, lastName);
        Student student = loginService.getCurrentStudent();

        List<Question> questions = questionService.getQuestions();

        for (Question question : questions) {
            System.out.println(question.getNumber() + ". " + messageSource.getMessage("question." + question.getNumber(),
                    null,
                    Locale.ENGLISH));
//            System.out.println(question.getNumber() + ". " + question.getQuestion());
            for (int i = 0; i < question.getAnswers().size(); i++) {
                System.out.println((i + 1) + ". " + question.getAnswers().get(i));
            }
            System.out.print("Print [1.." + question.getAnswers().size() + "] for answer: ");
            question.setAnswer(scanner.nextInt());
        }
        scanner.close();

        int mark = testingService.checkStudentAnswer(questions);

        System.out.println(student.getName() + " " + student.getLastName() + " your mark for the test " + mark);
    }

    @Override
    public void stopTesting() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getQuestion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int answer(int number) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getResult() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isStopped() {
        throw new UnsupportedOperationException();
    }

    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

    public void setQuestionService(IQuestionService questionService) {
        this.questionService = questionService;
    }

    public void setTestingService(ITestingService testingService) {
        this.testingService = testingService;
    }
}
