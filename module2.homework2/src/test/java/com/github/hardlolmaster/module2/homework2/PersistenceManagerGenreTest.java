package com.github.hardlolmaster.module2.homework2;

import com.github.hardlolmaster.module2.homework2.dao.IPersistenceManagerGenre;
import com.github.hardlolmaster.module2.homework2.domain.Genre;
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
public class PersistenceManagerGenreTest {
    @Autowired
    private IPersistenceManagerGenre persistenceManagerGenre;

    @Test
    public void testManager() {
        Genre genre = new Genre();
        genre.setName("Genre");
        persistenceManagerGenre.save(genre);
        assertNotNull(genre.getId());

        Genre byID = persistenceManagerGenre.getByID(genre.getId());
        assertNotNull(byID);
        assertEquals(genre, byID);

        Genre byName = persistenceManagerGenre.getByName(genre.getName());
        assertNotNull(byName);
        assertEquals(genre, byName);
    }
}
