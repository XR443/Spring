package com.github.hardlolmaster.module1.homework14.services;

import com.github.hardlolmaster.module1.homework14.dao.ILoginService;
import com.github.hardlolmaster.module1.homework14.domain.Student;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceImplTest extends TestCase {
    @Autowired
    private ILoginService loginService;

    @Test
    public void testLogin() {
        Student student = loginService.login("Name", "LastName");
        assertNotNull(student);
        assertEquals("Name", student.getName());
        assertEquals("LastName", student.getLastName());
    }
}