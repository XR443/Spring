package com.github.hardlolmaster.web;

import com.github.hardlolmaster.domain.Comment;
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
public class CommentControllerTest {
    @Autowired
    private CommentController controller;

    @Test
    public void test() {
        Comment comment = new Comment();
        comment.setMessage("Message");
        StepVerifier.create(controller.create(comment))
                .verifyComplete();
        StepVerifier.create(controller.comments())
                .expectNext(comment)
                .verifyComplete();
        comment.setMessage("Message2");
        StepVerifier.create(controller.update(comment))
                .verifyComplete();
        StepVerifier.create(controller.comments())
                .expectNext(comment)
                .verifyComplete();
        StepVerifier.create(controller.delete(comment))
                .verifyComplete();
        StepVerifier.create(controller.comments())
                .verifyComplete();
    }
}
