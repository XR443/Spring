package com.github.hardlolmaster.web;

import com.github.hardlolmaster.domain.Post;
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
public class PostControllerTest {
    @Autowired
    private PostController controller;

    @Test
    public void test() {
        Post post = new Post();
        post.setContent("Content");
        StepVerifier.create(controller.create(post))
                .verifyComplete();
        StepVerifier.create(controller.posts())
                .expectNext(post)
                .verifyComplete();
        post.setContent("Content2");
        StepVerifier.create(controller.update(post))
                .verifyComplete();
        StepVerifier.create(controller.posts())
                .expectNext(post)
                .verifyComplete();
        StepVerifier.create(controller.delete(post))
                .verifyComplete();
        StepVerifier.create(controller.posts())
                .verifyComplete();
    }
}
