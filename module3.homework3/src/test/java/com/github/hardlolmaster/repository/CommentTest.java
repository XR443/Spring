package com.github.hardlolmaster.repository;

import com.github.hardlolmaster.domain.Comment;
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
public class CommentTest {
    @Autowired
    private CommentRepository repository;

    @Test
    public void test() {
        Comment comment = new Comment();
        comment.setMessage("Message");

        Comment comment1 = new Comment();
        comment1.setMessage("Message1");

        Comment comment2 = new Comment();
        comment2.setMessage("Message2");

        StepVerifier.create(repository.save(comment))
                .expectNext(comment)
                .verifyComplete();

        StepVerifier.create(repository.findAll())
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier.create(repository.delete(comment))
                .verifyComplete();

        StepVerifier.create(repository.findAll())
                .verifyComplete();

        StepVerifier.create(repository.saveAll(asList(comment1, comment2)))
                .expectNext(comment1)
                .expectNext(comment2)
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
