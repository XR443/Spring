package com.github.hardlolmaster.module2.homework4;

import com.github.hardlolmaster.module2.homework4.dao.GenreRepository;
import com.github.hardlolmaster.module2.homework4.domain.Genre;
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
public class GenreTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void testGenreRepository() {
        Genre genre = new Genre();
        genre.setName("Genre");
        genreRepository.insert(genre);
        assertNotNull(genre.getId());

        Optional<Genre> byID = genreRepository.findById(genre.getId());
        assertNotNull(byID);
        assertTrue(byID.isPresent());
        assertEquals(genre, byID.get());

        Genre byName = genreRepository.findByName(genre.getName());
        assertNotNull(byName);
        assertEquals(genre, byName);

    }
}
