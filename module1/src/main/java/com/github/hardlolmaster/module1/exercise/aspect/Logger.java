package com.github.hardlolmaster.module1.exercise.aspect;

import com.github.hardlolmaster.module1.exercise.domain.Person;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logger {

    @Before("execution(* com.github.hardlolmaster.module1.exercise.dao.PersonDaoSimple.findByName(String))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Call " + joinPoint.getSignature().getName());
    }

    @After("execution(* com.github.hardlolmaster.module1.exercise.dao.PersonDaoSimple.findByName(String))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("End call " + joinPoint.getSignature().getName());
    }

    @Around("execution(* com.github.hardlolmaster.module1.exercise.dao.PersonDaoSimple.findByName(String))")
    public Person logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Now I will call " + joinPoint.getSignature().getName()
                + " from " + joinPoint.getSignature().getDeclaringTypeName());
        Person person = (Person) joinPoint.proceed(joinPoint.getArgs());
        System.out.println("Now I will return the value of the method " + joinPoint.getSignature().getName()
                + " from " + joinPoint.getSignature().getDeclaringTypeName());
        return person;
    }
}
