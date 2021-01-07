package com.github.hardlolmaster.module2.homework1.dao;

import com.github.hardlolmaster.module2.homework1.domain.Genre;
import org.junit.Before;
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
public class GenreDaoTest {
    @Autowired
    private GenreDao genreDao;

    @Test
    public void testDao() {
        Genre genre = new Genre(1L, "Genre");
        genreDao.insert(genre);
        genreDao.insert(new Genre(2L, "Genre2"));
        genreDao.insert(new Genre(3L, "Genre3"));

        Genre byId = genreDao.findById(genre.getId());
        assertNotNull(byId);
        assertEquals(genre, byId);

        genre.setName("Genre1");
        genreDao.update(genre);
        byId = genreDao.findById(genre.getId());
        assertEquals(genre, byId);

        List<Genre> all = genreDao.findAll();
        assertNotNull(all);
        assertEquals(3, all.size());

        genreDao.deleteById(genre.getId());

        all = genreDao.findAll();
        assertNotNull(all);
        assertEquals(2, all.size());

        try {
            byId = genreDao.findById(genre.getId());
            fail("Genre has not been deleted");
            assertNull(byId);
        } catch (Exception ignored) {

        }

    }

}