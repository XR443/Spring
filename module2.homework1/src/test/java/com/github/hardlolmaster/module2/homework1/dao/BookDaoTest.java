package com.github.hardlolmaster.module2.homework1.dao;

import com.github.hardlolmaster.module2.homework1.domain.Author;
import com.github.hardlolmaster.module2.homework1.domain.Book;
import com.github.hardlolmaster.module2.homework1.domain.Genre;
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
public class BookDaoTest {
    @Autowired
    private GenreDao genreDao;
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private BookDao bookDao;

    @Test
    public void testDao() {
        Genre genre = new Genre(1L, "Genre");
        genreDao.insert(genre);
        Author author = new Author(1L, "Name", "LastName");
        authorDao.insert(author);

        Book book = new Book(1L, "Book", genre, author);
        bookDao.insert(book);
        bookDao.insert(new Book(2L, "Book2", genre, author));
        bookDao.insert(new Book(3L, "Book3", genre, author));

        Book byId = bookDao.findById(book.getId());
        assertNotNull(byId);
        assertEquals(book, byId);
        assertEquals(book.getAuthor(), byId.getAuthor());
        assertEquals(book.getGenre(), byId.getGenre());

        book.setName("Book1");
        bookDao.update(book);
        byId = bookDao.findById(book.getId());
        assertEquals(book, byId);
        assertEquals(book.getAuthor(), byId.getAuthor());
        assertEquals(book.getGenre(), byId.getGenre());

        List<Book> all = bookDao.findAll();
        assertNotNull(all);
        assertEquals(3, all.size());

        bookDao.deleteById(book.getId());

        all = bookDao.findAll();
        assertNotNull(all);
        assertEquals(2, all.size());

        try {
            byId = bookDao.findById(book.getId());
            fail("Author has not been deleted");
            assertNull(byId);
        } catch (Exception ignored) {

        }
    }
}
