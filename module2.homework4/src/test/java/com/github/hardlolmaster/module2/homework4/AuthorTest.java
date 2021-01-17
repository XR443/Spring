package com.github.hardlolmaster.module2.homework4;

import com.github.hardlolmaster.module2.homework4.dao.AuthorRepository;
import com.github.hardlolmaster.module2.homework4.domain.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class AuthorTest extends MongoStart {

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
