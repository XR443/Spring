package com.github.hardlolmaster.module2.homework1.dao;

import com.github.hardlolmaster.module2.homework1.domain.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthorDaoTest {
    @Autowired
    private AuthorDao authorDao;

    @Test
    public void testDao() {
        Author author = new Author(1L, "Author", "Author");
        authorDao.insert(author);
        authorDao.insert(new Author(2L, "Author2", "Author2"));
        authorDao.insert(new Author(3L, "Author3", "Author3"));

        Author byId = authorDao.findById(author.getId());
        assertNotNull(byId);
        assertEquals(author, byId);

        author.setName("Author1");
        authorDao.update(author);
        byId = authorDao.findById(author.getId());
        assertEquals(author, byId);

        List<Author> all = authorDao.findAll();
        assertNotNull(all);
        assertEquals(3, all.size());

        authorDao.deleteById(author.getId());

        all = authorDao.findAll();
        assertNotNull(all);
        assertEquals(2, all.size());

        try {
            byId = authorDao.findById(author.getId());
            fail("Author has not been deleted");
            assertNull(byId);
        } catch (Exception ignored) {

        }

    }
}
