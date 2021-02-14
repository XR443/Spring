package com.github.hardlolmaster.repository;

import com.github.hardlolmaster.domain.Post;
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
public class PostTest {
    @Autowired
    private PostRepository repository;

    @Test
    public void test() {
        Post post = new Post();
        post.setContent("UserName");
        Post post1 = new Post();
        post1.setContent("UserName1");
        Post post2 = new Post();
        post2.setContent("UserName2");

        StepVerifier.create(repository.save(post))
                .expectNext(post)
                .verifyComplete();

        StepVerifier.create(repository.findAll())
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier.create(repository.delete(post))
                .verifyComplete();

        StepVerifier.create(repository.findAll())
                .verifyComplete();

        StepVerifier.create(repository.saveAll(asList(post1, post2)))
                .expectNext(post1)
                .expectNext(post2)
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
