package com.github.hardlolmaster.module1.homework.services;

import com.github.hardlolmaster.module1.homework.Properties;
import com.github.hardlolmaster.module1.homework.dao.CsvFileQuestionService;
import com.github.hardlolmaster.module1.homework.dao.IQuestionService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ShellTestingSystemTest extends TestCase {
    @Autowired
    private ITestingSystemService testingSystemService;

    @Test
    public void testShellTestingSystem() {
        assertTrue(testingSystemService.isStopped());
        testingSystemService.startTesting();
        assertFalse(testingSystemService.isStopped());
        testingSystemService.answer(1);
        testingSystemService.answer(1);
        testingSystemService.answer(1);
        testingSystemService.answer(1);
        testingSystemService.answer(1);
        assertEquals(-1, testingSystemService.getResult());
        testingSystemService.stopTesting();
        assertTrue(testingSystemService.isStopped());
        assertEquals(2, testingSystemService.getResult());
    }

    @TestConfiguration
    static class ShellTestingSystemTestConfiguration {
        public IQuestionService questionService() {
            Properties properties = new Properties();
            properties.setCsv(new Properties.Csv());
            properties.getCsv().setPath("src/test/resources/module1/homework/test/test.csv");
            return new CsvFileQuestionService(properties);
        }

        public ITestingService testingService() {
            return new TestingServiceImpl();
        }

        @Bean
        public ITestingSystemService testingSystemService() {
            return new ShellTestingSystemServiceImpl(questionService(), testingService(), null);
        }
    }
}
