package com.github.hardlolmaster.module1.homework.services;

import com.github.hardlolmaster.module1.homework.dao.IQuestionService;
import com.github.hardlolmaster.module1.homework.domain.Question;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CsvFileQuestionServiceTest extends TestCase {
    @Autowired
    private IQuestionService service;

    @Test
    public void testGetQuestions() {
        List<Question> list = service.getQuestions();
        assertEquals(5, list.size());
    }
}