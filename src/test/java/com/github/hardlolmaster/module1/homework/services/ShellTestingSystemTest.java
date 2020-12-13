package com.github.hardlolmaster.module1.homework.services;

import com.github.hardlolmaster.module1.homework.Properties;
import com.github.hardlolmaster.module1.homework.dao.IQuestionService;
import com.github.hardlolmaster.module1.homework.domain.Question;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class ShellTestingSystemTest extends TestCase {
    @Autowired
    private ITestingSystemService testingSystemService;

    @Test
    public void testShellTestingSystem() {
        assertTrue(testingSystemService.isStopped());

        testingSystemService.startTesting();
        assertFalse(testingSystemService.isStopped());
        assertEquals(0, testingSystemService.answer(1));
        assertEquals(0, testingSystemService.answer(1));
        assertEquals(0, testingSystemService.answer(1));
        assertEquals(0, testingSystemService.answer(1));
        assertEquals(0, testingSystemService.answer(1));
        assertEquals(-1, testingSystemService.answer(1));
        assertEquals(-1, testingSystemService.getResult());

        testingSystemService.stopTesting();
        assertTrue(testingSystemService.isStopped());
        assertEquals(10, testingSystemService.getResult());
    }

    @TestConfiguration
    static class ShellTestingSystemTestConfiguration {

        public IQuestionService questionService() {
            return new IQuestionService() {
                private List<Question> list = new ArrayList<>();

                @Override
                public List<Question> regenerate() {
                    for (int i = 1; i <= 5; i++) {
                        Question question = new Question();
                        list.add(question);
                    }
                    return list;
                }

                @Override
                public List<Question> getQuestions() {
                    return list;
                }
            };
        }

        public ITestingService testingService() {
            return new ITestingService() {
                @Override
                public int checkStudentAnswer(List<Question> questions) {
                    return 10;
                }
            };
        }

        @Bean
        public ITestingSystemService testingSystemService() {
            return new ShellTestingSystemServiceImpl(questionService(), testingService(), null, new Properties());
        }
    }
}
