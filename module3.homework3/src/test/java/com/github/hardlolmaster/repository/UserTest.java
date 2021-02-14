package com.github.hardlolmaster.repository;

import com.github.hardlolmaster.domain.User;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import static java.util.Arrays.asList;

@RunWith(SpringRunner.class)
@DataMongoTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserTest {
    @Autowired
    private UserRepository repository;

    @Test
    public void test() {
        User user = new User();
        user.setName("UserName");
        User user1 = new User();
        user1.setName("UserName1");
        User user2 = new User();
        user2.setName("UserName2");

        StepVerifier.create(repository.save(user))
                .expectNext(user)
                .verifyComplete();

        StepVerifier.create(repository.findAll())
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier.create(repository.delete(user))
                .verifyComplete();

        StepVerifier.create(repository.findAll())
                .verifyComplete();

        StepVerifier.create(repository.saveAll(asList(user1, user2)))
                .expectNext(user1)
                .expectNext(user2)
                .verifyComplete();

        StepVerifier.create(repository.findAll())
                .expectNextCount(2)
                .verifyComplete();

        StepVerifier.create(repository.deleteAll())
                .verifyComplete();

        StepVerifier.create(repository.findAll())
                .verifyComplete();
    }
}
