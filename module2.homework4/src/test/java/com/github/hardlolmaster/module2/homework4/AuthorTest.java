package com.github.hardlolmaster.module2.homework4;

import com.github.hardlolmaster.module2.homework4.dao.AuthorRepository;
import com.github.hardlolmaster.module2.homework4.domain.Author;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthorTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testAuthorRepository() {
        Author author = new Author();
        author.setName("Name");
        author.setLastName("LastName");
        authorRepository.insert(author);
        assertNotNull(author.getId());

        Optional<Author> byID = authorRepository.findById(author.getId());
        assertNotNull(byID);
        assertTrue(byID.isPresent());
        assertEquals(author, byID.get());

        Author byName = authorRepository.findByNameAndLastName(author.getName(), author.getLastName());
        assertNotNull(byName);
        assertEquals(author, byName);

    }
}
