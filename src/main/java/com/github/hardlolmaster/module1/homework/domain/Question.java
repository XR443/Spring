package com.github.hardlolmaster.module1.homework.domain;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private int number;
    private String question;
    private List<String> answers;
    private int rightAnswer;
    private int answer;

    public Question() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        if (answers == null)
            answers = new ArrayList<>();
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void addAnswer(String answer) {
        getAnswers().add(answer);
    }
}
