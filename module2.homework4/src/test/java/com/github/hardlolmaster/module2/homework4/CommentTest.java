package com.github.hardlolmaster.module2.homework4;

import com.github.hardlolmaster.module2.homework4.dao.AuthorRepository;
import com.github.hardlolmaster.module2.homework4.dao.BookRepository;
import com.github.hardlolmaster.module2.homework4.dao.CommentRepository;
import com.github.hardlolmaster.module2.homework4.dao.GenreRepository;
import com.github.hardlolmaster.module2.homework4.domain.Author;
import com.github.hardlolmaster.module2.homework4.domain.Book;
import com.github.hardlolmaster.module2.homework4.domain.Comment;
import com.github.hardlolmaster.module2.homework4.domain.Genre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class CommentTest extends MongoStart {

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void testCommentRepository() {
        Genre genre = new Genre();
        genre.setName("Genre");
        genreRepository.save(genre);
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

        Comment comment = new Comment();
        comment.setUsername("Username");
        comment.setMessage("Message");
        comment.setBook(book);

        book.getComments().add(comment);

        commentRepository.save(comment);
        assertNotNull(book.getId());
        assertNotNull(author.getId());
        assertNotNull(genre.getId());
        assertNotNull(comment.getId());

        Optional<Comment> byID = commentRepository.findById(comment.getId());
        assertNotNull(byID);
        assertTrue(byID.isPresent());
        assertEquals(comment, byID.get());
    }
}
