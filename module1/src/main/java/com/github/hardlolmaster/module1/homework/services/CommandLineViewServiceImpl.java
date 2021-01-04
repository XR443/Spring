package com.github.hardlolmaster.module1.homework.services;

import com.github.hardlolmaster.module1.homework.Properties;
import com.github.hardlolmaster.module1.homework.domain.Question;
import com.github.hardlolmaster.module1.homework.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CommandLineViewServiceImpl implements IViewService {

    private final Properties properties;
    private final MessageSource messageSource;

    @Autowired
    public CommandLineViewServiceImpl(Properties properties, MessageSource messageSource) {
        this.properties = properties;
        this.messageSource = messageSource;
    }

    @Override
    public void greetings() {
        System.out.println("WELCOME TO THE TEST SYSTEM");
    }

    @Override
    public void enter(String whatEnter) {
        System.out.printf("Enter your %s: ", whatEnter);
    }

    @Override
    public void showQuestion(Question question) {
        Locale locale = new Locale(properties.getLocale());
        System.out.println(question.getNumber() + ". " + messageSource.getMessage("question." + question.getNumber(),
                null,
                locale));
        for (int i = 0; i < question.getAnswers().size(); i++) {
            System.out.println((i + 1) + ". " + question.getAnswers().get(i));
        }
        System.out.print("Print [1.." + question.getAnswers().size() + "] for answer: ");
    }

    @Override
    public void showResults(Student student, int mark) {
        System.out.println(student.getName() + " " + student.getLastName() + " your mark for the test " + mark);
    }
}
