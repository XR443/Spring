package com.github.hardlolmaster.module1.homework14.services;

import com.github.hardlolmaster.module1.homework14.dao.ILoginService;
import com.github.hardlolmaster.module1.homework14.dao.IQuestionService;
import com.github.hardlolmaster.module1.homework14.domain.Question;
import com.github.hardlolmaster.module1.homework14.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Service
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

        Student student = loginService.login(name, lastName);

        List<Question> questions = questionService.getQuestions();

        for (Question question : questions) {
            System.out.println(question.getNumber() + ". "+ messageSource.getMessage("question." + question.getNumber(),
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
