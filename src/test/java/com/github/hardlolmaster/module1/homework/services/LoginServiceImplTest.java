package com.github.hardlolmaster.module1.homework.services;

import com.github.hardlolmaster.module1.homework.dao.ILoginService;
import com.github.hardlolmaster.module1.homework.dao.LoginServiceImpl;
import com.github.hardlolmaster.module1.homework.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class LoginServiceImplTest {
    @Autowired
    private ILoginService loginService;

    @Test
    public void testLogin() {
        loginService.login("Name", "LastName");
        Student student = loginService.getCurrentStudent();
        assertNotNull(student);
        assertEquals("Name", student.getName());
        assertEquals("LastName", student.getLastName());
        loginService.logout();
        student = loginService.getCurrentStudent();
        assertNull(student);
    }

    @TestConfiguration
    static class LoginServiceImplTestConfiguration {
        @Bean
        public ILoginService loginService() {
            return new LoginServiceImpl();
        }
    }
}