package com.github.hardlolmaster.module1.exercise;

import com.github.hardlolmaster.module1.exercise.domain.Person;
import com.github.hardlolmaster.module1.exercise.service.IPersonService;
import com.github.hardlolmaster.module1.exercise.service.PersonServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLOutput;

@EnableAspectJAutoProxy
@ComponentScan(basePackages =
        {"com.github.hardlolmaster.module1.exercise.dao",
                "com.github.hardlolmaster.module1.exercise.service",
                "com.github.hardlolmaster.module1.exercise.aspect"})
public class Main {

    public static void main(String[] args) {
        System.out.println("Constructor based context\n▼▼▼▼▼▼▼▼▼▼▼▼");
        {
            ApplicationContext context = getConstructorContext();
            IPersonService s = context.getBean(PersonServiceImpl.class);
            Person person = s.getByName("Ivan");
            System.out.println(person.getName() + ", what's your name? Are you " + person.getAge() + " years old?");
        }
        System.out.println("*****************\nProperty based context\n▼▼▼▼▼▼▼▼▼▼▼▼");
        {
            ApplicationContext context = getPropertyContext();
            IPersonService s = context.getBean(PersonServiceImpl.class);
            Person person = s.getByName("Maxim");
            System.out.println(person.getName() + ", what's your name? Are you " + person.getAge() + " years old?");
        }
        System.out.println("*****************\nAnnotation based context\n▼▼▼▼▼▼▼▼▼▼▼▼");
        {
            ApplicationContext context = getAnnotationContext();
            IPersonService s = context.getBean(PersonServiceImpl.class);
            Person person = s.getByName("Dmitriy");
            System.out.println(person.getName() + ", what's your name? Are you " + person.getAge() + " years old?");
        }
    }

    private static ApplicationContext getConstructorContext() {
        return new ClassPathXmlApplicationContext("module1/exercise/context-constructor.xml");
    }

    private static ApplicationContext getPropertyContext() {
        return new ClassPathXmlApplicationContext("module1/exercise/context-property.xml");
    }

    private static ApplicationContext getAnnotationContext() {
        return new AnnotationConfigApplicationContext(Main.class);
    }

}
