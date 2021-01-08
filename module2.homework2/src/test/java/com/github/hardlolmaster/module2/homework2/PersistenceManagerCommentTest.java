package com.github.hardlolmaster.module2.homework2;

import com.github.hardlolmaster.module2.homework2.dao.IPersistenceManagerAuthor;
import com.github.hardlolmaster.module2.homework2.dao.IPersistenceManagerBook;
import com.github.hardlolmaster.module2.homework2.dao.IPersistenceManagerComment;
import com.github.hardlolmaster.module2.homework2.dao.IPersistenceManagerGenre;
import com.github.hardlolmaster.module2.homework2.domain.Author;
import com.github.hardlolmaster.module2.homework2.domain.Book;
import com.github.hardlolmaster.module2.homework2.domain.Comment;
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

        Comment byID = managerComment.getByID(comment.getId());
        assertNotNull(byID);
        assertEquals(comment, byID);
    }

}
