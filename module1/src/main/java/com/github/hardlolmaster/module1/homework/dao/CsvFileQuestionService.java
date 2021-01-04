package com.github.hardlolmaster.module1.homework.dao;

import com.github.hardlolmaster.module1.homework.Properties;
import com.github.hardlolmaster.module1.homework.domain.Question;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class CsvFileQuestionService implements IQuestionService {

    private String filePath;
    private List<Question> questions;
    private Pattern number = Pattern.compile("\\d*");

    public CsvFileQuestionService(Properties properties) {
        this.filePath = properties.getCsv().getPath();
    }

    @Override
    public List<Question> regenerate() {
        File file = new File(filePath);

        List<Question> result = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            result = bufferedReader.lines().map(this::parseQuestionLine).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        questions = result;
        return result;
    }

    private Optional<Question> parseQuestionLine(String line) {
        String[] strArr = line.split(",");
        if (!number.matcher(strArr[0]).matches()) {
            return Optional.empty();
        }
        List<String> answers = new ArrayList<>();
        for (int i = 1; i < strArr.length - 2; ) {
            answers.add(strArr[++i]);
        }
        Question build = Question.builder()
                .number(Integer.parseInt(strArr[0]))
                .question(strArr[1])
                .answers(answers)
                .rightAnswer(Integer.parseInt(strArr[strArr.length - 1]))
                .build();
        return Optional.of(build);
    }

    @Override
    public List<Question> getQuestions() {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        return questions;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
