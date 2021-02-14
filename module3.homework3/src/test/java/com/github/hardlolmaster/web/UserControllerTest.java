package com.github.hardlolmaster.web;

import com.github.hardlolmaster.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserControllerTest {
    @Autowired
    private UserController controller;

    @Test
    public void test() {
        User user = new User();
        user.setName("UserName");
        StepVerifier.create(controller.create(user))
                .verifyComplete();
        StepVerifier.create(controller.users())
                .expectNext(user)
                .verifyComplete();
        user.setName("UserName2");
        StepVerifier.create(controller.update(user))
                .verifyComplete();
        StepVerifier.create(controller.users())
                .expectNext(user)
                .verifyComplete();
        StepVerifier.create(controller.delete(user))
                .verifyComplete();
        StepVerifier.create(controller.users())
                .verifyComplete();
    }
}
