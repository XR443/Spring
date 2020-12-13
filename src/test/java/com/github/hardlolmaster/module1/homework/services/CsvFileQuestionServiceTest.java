package com.github.hardlolmaster.module1.homework.services;

import com.github.hardlolmaster.module1.homework.Properties;
import com.github.hardlolmaster.module1.homework.dao.CsvFileQuestionService;
import com.github.hardlolmaster.module1.homework.dao.IQuestionService;
import com.github.hardlolmaster.module1.homework.domain.Question;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
public class CsvFileQuestionServiceTest extends TestCase {
    @Autowired
    private IQuestionService service;

    @Test
    public void testGetQuestions() {
        List<Question> list;
        list = service.getQuestions();
        assertEquals(0, list.size());
        list = service.regenerate();
        assertEquals(5, list.size());
        list = service.getQuestions();
        assertEquals(5, list.size());
        service.getQuestions().clear();
        list = service.getQuestions();
        assertEquals(0, list.size());
    }

    @TestConfiguration
    static class CsvFileQuestionServiceTestConfiguration {
        @Bean
        public IQuestionService questionService() {
            Properties properties = new Properties();
            properties.getCsv().setPath("src/test/resources/module1/homework/test/test.csv");
            return new CsvFileQuestionService(properties);
        }
    }
}