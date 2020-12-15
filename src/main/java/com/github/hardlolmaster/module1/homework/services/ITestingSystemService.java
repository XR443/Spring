package com.github.hardlolmaster.module1.homework.services;

public interface ITestingSystemService {
    void startTesting();

    void stopTesting();

    int getQuestion();

    int answer(int number);

    int getResult();

    boolean isStopped();
}
