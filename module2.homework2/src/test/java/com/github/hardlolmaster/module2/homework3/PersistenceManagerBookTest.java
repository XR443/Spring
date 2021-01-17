package com.github.hardlolmaster.module2.homework3;

import com.github.hardlolmaster.module2.homework3.dao.IPersistenceManagerAuthor;
import com.github.hardlolmaster.module2.homework3.dao.IPersistenceManagerBook;
import com.github.hardlolmaster.module2.homework3.dao.IPersistenceManagerGenre;
import com.github.hardlolmaster.module2.homework3.domain.Author;
import com.github.hardlolmaster.module2.homework3.domain.Book;
import com.github.hardlolmaster.module2.homework3.domain.Genre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PersistenceManagerBookTest {
    @Autowired
    private IPersistenceManagerBook managerBook;
    @Autowired
    private IPersistenceManagerGenre managerGenre;
    @Autowired
    private IPersistenceManagerAuthor managerAuthor;

    @Test
    public void testManager() {
        Genre genre = new Genre();
        genre.setName("Genre");
        managerGenre.save(genre);
        assertNotNull(genre.getId());

        Author author = new Author();
        author.setName("Name");
        author.setLastName("LastName");
        managerAuthor.save(author);
        assertNotNull(author.getId());

        Book book = new Book();
        book.setName("Book");
        book.setAuthor(author);
        book.setGenre(genre);
        managerBook.save(book);
        assertNotNull(book.getId());

        Book byID = managerBook.getByID(book.getId());
        assertNotNull(byID);
        assertEquals(book, byID);

        Book byName = managerBook.getByName(book.getName());
        assertNotNull(byName);
        assertEquals(book, byName);

        List<Book> booksByAuthor = managerBook.getBooksByAuthor(author);
        assertNotNull(booksByAuthor);
        assertEquals(1, booksByAuthor.size());
        assertEquals(book, booksByAuthor.get(0));

        List<Book> booksByGenre = managerBook.getBooksByGenre(genre);
        assertNotNull(booksByGenre);
        assertEquals(1, booksByGenre.size());
        assertEquals(book, booksByGenre.get(0));
    }
}
