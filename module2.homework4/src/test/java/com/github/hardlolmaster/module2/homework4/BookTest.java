package com.github.hardlolmaster.module2.homework4;

import com.github.hardlolmaster.module2.homework4.dao.AuthorRepository;
import com.github.hardlolmaster.module2.homework4.dao.BookRepository;
import com.github.hardlolmaster.module2.homework4.dao.GenreRepository;
import com.github.hardlolmaster.module2.homework4.domain.Author;
import com.github.hardlolmaster.module2.homework4.domain.Book;
import com.github.hardlolmaster.module2.homework4.domain.Genre;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookTest  {

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testBookRepository() {
        Genre genre = new Genre();
        genre.setName("Genre");
        genreRepository.insert(genre);
        assertNotNull(genre.getId());

        Author author = new Author();
        author.setName("Name");
        author.setLastName("LastName");
        authorRepository.save(author);
        assertNotNull(author.getId());

        Book book = new Book();
        book.setName("Book");
        book.setAuthor(author);
        book.setGenre(genre);
        bookRepository.save(book);
        assertNotNull(book.getId());

        Optional<Book> byID = bookRepository.findById(book.getId());
        assertNotNull(byID);
        assertTrue(byID.isPresent());
        assertEquals(book, byID.get());

        Book byName = bookRepository.findByName(book.getName());
        assertNotNull(byName);
        assertEquals(book, byName);

        List<Book> booksByAuthor = bookRepository.findBooksByAuthor(author);
        assertNotNull(booksByAuthor);
        assertEquals(1, booksByAuthor.size());
        assertEquals(book, booksByAuthor.get(0));

        List<Book> booksByGenre = bookRepository.findBooksByGenre(genre);
        assertNotNull(booksByGenre);
        assertEquals(1, booksByGenre.size());
        assertEquals(book, booksByGenre.get(0));

    }
}
