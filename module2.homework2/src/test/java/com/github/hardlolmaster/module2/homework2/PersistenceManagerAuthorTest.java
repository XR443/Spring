package com.github.hardlolmaster.module2.homework2;

import com.github.hardlolmaster.module2.homework2.dao.IPersistenceManagerAuthor;
import com.github.hardlolmaster.module2.homework2.domain.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

        Author byID = managerAuthor.getByID(author.getId());
        assertNotNull(byID);
        assertEquals(author, byID);

        Author byName = managerAuthor.getByName(author.getName(), author.getLastName());
        assertNotNull(byName);
        assertEquals(author, byName);
    }
}
