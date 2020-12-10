package com.github.hardlolmaster.module1.homework.shell;

import com.github.hardlolmaster.module1.homework.dao.ILoginService;
import com.github.hardlolmaster.module1.homework.domain.Student;
import com.github.hardlolmaster.module1.homework.services.ITestingService;
import com.github.hardlolmaster.module1.homework.services.ITestingSystemService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class Commands {
    private final ILoginService loginService;
    private final ITestingService testingService;
    private final ITestingSystemService testingSystemService;

    public Commands(ILoginService loginService,
                    ITestingService testingService,
                    @Qualifier(value = "shellLineTestSystem") ITestingSystemService testingSystemService) {
        this.loginService = loginService;
        this.testingService = testingService;
        this.testingSystemService = testingSystemService;
    }

    @ShellMethod(value = "LogIn command", key = {"login"})
    public void login(@ShellOption() String name, @ShellOption String surname) {
        loginService.login(name, surname);
        System.out.println("Welcome " + name + " " + surname);
        System.out.println("Use 'start' for start testing");
    }

    @ShellMethod(value = "LogOut command", key = {"logout"})
    public void logout() {
        loginService.logout();
        System.out.println("Good Luck");
    }

    @ShellMethod(value = "Start testing command", key = {"start"})
    public void startTesting() {
        if (loginService.getCurrentStudent() == null) {
            System.out.println("Please login with using 'login --name yourName --surname yourSurname'");
            return;
        }
        testingSystemService.startTesting();
        System.out.println("Testing started");
        System.out.println("Use 'question' for new question");
        System.out.println("Use 'answer --number %number%' for answer");
        System.out.println("Use 'stop' for stop testing");
    }

    @ShellMethod(value = "Stop testing command", key = {"stop"})
    public void stopTesting() {
        if (isNotStarted()) return;
        testingSystemService.stopTesting();
        System.out.println("Testing stopped");
        System.out.println("Use 'start' for rerun");
        System.out.println("Use 'result' for see results");
    }

    @ShellMethod(value = "Question", key = {"question", "q"})
    public void question() {
        if (isNotStarted()) return;
        testingSystemService.getQuestion();
    }

    @ShellMethod(value = "Answer command", key = {"answer"})
    public void answer(@ShellOption int number) {
        if (isNotStarted()) return;
        testingSystemService.answer(number);
        System.out.println("Next question is ready");
        System.out.println("Use 'question' for next question");
    }

    @ShellMethod(value = "Result command", key = {"result"})
    public void result() {
        if(testingSystemService.getResult()==-1){
            System.out.println("Stop testing first");
            return;
        }
        Student user = loginService.getCurrentStudent();
        System.out.println(user.getName() + " " + user.getLastName() + " your grade is " + testingSystemService.getResult());
    }

    private boolean isNotStarted() {
        if (testingSystemService.isStopped()) {
            System.out.println("Start testing first");
            return true;
        }
        return false;
    }
}
