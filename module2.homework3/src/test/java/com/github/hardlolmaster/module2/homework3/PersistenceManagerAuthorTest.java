package com.github.hardlolmaster.module2.homework3;

import com.github.hardlolmaster.module2.homework3.dao.IPersistenceManagerAuthor;
import com.github.hardlolmaster.module2.homework3.domain.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class PersistenceManagerAuthorTest {
    @Autowired
    private IPersistenceManagerAuthor managerAuthor;

    @Test
    public void testManager() {
        Author author = new Author();
        author.setName("Name");
        author.setLastName("LastName");
        managerAuthor.save(author);
        assertNotNull(author.getId());

        Optional<Author> byID = managerAuthor.findById(author.getId());
        assertNotNull(byID);
        assertTrue(byID.isPresent());
        assertEquals(author, byID.get());

        Author byName = managerAuthor.findByNameAndLastName(author.getName(), author.getLastName());
        assertNotNull(byName);
        assertEquals(author, byName);
    }
}
