package com.github.hardlolmaster.module1.homework.services;

import com.github.hardlolmaster.module1.homework.Properties;
import com.github.hardlolmaster.module1.homework.dao.CsvFileQuestionService;
import com.github.hardlolmaster.module1.homework.dao.IQuestionService;
import com.github.hardlolmaster.module1.homework.domain.Question;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class TestingServiceImplTest extends TestCase {

    @Autowired
    private ITestingService testingService;

    @Test
    public void testCheckStudentAnswer() {
        List<Question> list = new ArrayList<>();
        assertEquals(-1, testingService.checkStudentAnswer(list));
        for (int i = 1; i <= 5; i += 1) {
            Question question = new Question();
            question.setAnswer(i);
            question.setRightAnswer(1);
            list.add(question);
        }
        assertEquals(1, testingService.checkStudentAnswer(list));
    }
    @TestConfiguration
    static class TestingServiceImplTestConfiguration {
        @Bean
        public ITestingService testingService() {
            return new TestingServiceImpl();
        }
    }
}