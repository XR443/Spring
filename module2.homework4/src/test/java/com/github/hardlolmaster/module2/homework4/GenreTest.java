package com.github.hardlolmaster.module2.homework4;

import com.github.hardlolmaster.module2.homework4.dao.GenreRepository;
import com.github.hardlolmaster.module2.homework4.domain.Genre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class GenreTest extends MongoStart {

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
