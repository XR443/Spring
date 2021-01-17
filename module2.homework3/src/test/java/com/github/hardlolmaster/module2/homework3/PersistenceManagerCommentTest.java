package com.github.hardlolmaster.module2.homework3;

import com.github.hardlolmaster.module2.homework3.dao.IPersistenceManagerAuthor;
import com.github.hardlolmaster.module2.homework3.dao.IPersistenceManagerBook;
import com.github.hardlolmaster.module2.homework3.dao.IPersistenceManagerComment;
import com.github.hardlolmaster.module2.homework3.dao.IPersistenceManagerGenre;
import com.github.hardlolmaster.module2.homework3.domain.Author;
import com.github.hardlolmaster.module2.homework3.domain.Book;
import com.github.hardlolmaster.module2.homework3.domain.Comment;
import com.github.hardlolmaster.module2.homework3.domain.Genre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PersistenceManagerCommentTest {
    @Autowired
    private IPersistenceManagerComment managerComment;
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

        Comment comment = new Comment();
        comment.setUsername("Username");
        comment.setMessage("Message");
        comment.setBook(book);

        book.getComments().add(comment);

        managerComment.save(comment);
        assertNotNull(book.getId());
        assertNotNull(author.getId());
        assertNotNull(genre.getId());
        assertNotNull(comment.getId());

        Optional<Comment> byID = managerComment.findById(comment.getId());
        assertNotNull(byID);
        assertTrue(byID.isPresent());
        assertEquals(comment, byID.get());
    }

}
