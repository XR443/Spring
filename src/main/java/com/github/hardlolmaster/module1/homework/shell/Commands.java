package com.github.hardlolmaster.module1.homework.shell;

import com.github.hardlolmaster.module1.homework.services.ITestingSystemService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class Commands {
    private final ITestingSystemService testingSystemService;

    public Commands(ITestingSystemService testingSystemService) {
        this.testingSystemService = testingSystemService;
    }

    @ShellMethod(value = "Start testing command", key = {"start"})
    public void startTesting() {
        testingSystemService.startTesting();
    }
}
