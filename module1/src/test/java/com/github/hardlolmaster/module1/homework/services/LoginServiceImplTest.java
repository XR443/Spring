package com.github.hardlolmaster.module1.homework.services;

import com.github.hardlolmaster.module1.homework.dao.ILoginService;
import com.github.hardlolmaster.module1.homework.dao.LoginServiceImpl;
import com.github.hardlolmaster.module1.homework.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
        String name = "Name";
        String lastName = "LastName";

        int login = loginService.login(name, lastName);
        assertEquals(0, login);
        Student student = loginService.getCurrentStudent();
        assertNotNull(student);
        assertEquals(name, student.getName());
        assertEquals(lastName, student.getLastName());

        login = loginService.login(name, lastName);
        assertEquals(-1, login);

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