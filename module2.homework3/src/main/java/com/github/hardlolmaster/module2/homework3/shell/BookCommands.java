package com.github.hardlolmaster.module2.homework3.shell;

import com.github.hardlolmaster.module2.homework3.dao.IPersistenceManagerAuthor;
import com.github.hardlolmaster.module2.homework3.dao.IPersistenceManagerBook;
import com.github.hardlolmaster.module2.homework3.dao.IPersistenceManagerComment;
import com.github.hardlolmaster.module2.homework3.dao.IPersistenceManagerGenre;
import com.github.hardlolmaster.module2.homework3.domain.Author;
import com.github.hardlolmaster.module2.homework3.domain.Book;
import com.github.hardlolmaster.module2.homework3.domain.Comment;
import com.github.hardlolmaster.module2.homework3.domain.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ShellComponent
@Transactional
public class BookCommands {
    private final IPersistenceManagerBook managerBook;
    private final IPersistenceManagerAuthor managerAuthor;
    private final IPersistenceManagerGenre managerGenre;
    private final IPersistenceManagerComment managerComment;

    @Autowired
    public BookCommands(IPersistenceManagerBook managerBook,
                        IPersistenceManagerAuthor managerAuthor,
                        IPersistenceManagerGenre managerGenre,
                        IPersistenceManagerComment managerComment) {
        this.managerBook = managerBook;
        this.managerAuthor = managerAuthor;
        this.managerGenre = managerGenre;
        this.managerComment = managerComment;
    }

    @ShellMethod(value = "Insert book", key = "insert-book")
    public void insertBook(@ShellOption String name,
                           @ShellOption long genreId,
                           @ShellOption long authorId) {
        Optional<Author> optionalAuthor = managerAuthor.findById(authorId);
        if (optionalAuthor.isEmpty())
            throw new RuntimeException("ATTENTION AUTHOR NOT FOUND!!!!!ONE!!!");
        Author author = optionalAuthor.get();

        Optional<Genre> genreOptional = managerGenre.findById(genreId);
        if (genreOptional.isEmpty())
            throw new RuntimeException("ATTENTION COMMENT NOT FOUND!!!!!ONE!!!");

        Genre genre = genreOptional.get();
        Book object = new Book();
        object.setAuthor(author);
        object.setGenre(genre);
        object.setName(name);
        object.setComments(new ArrayList<>());
        managerBook.save(object);
        System.out.println(object);
    }

    @ShellMethod(value = "Get all books", key = "get-all-book")
    public void getAllBooks() {
        System.out.println(managerBook.findAll());
    }

    @ShellMethod(value = "Get book", key = "get-book")
    public void getBookById(@ShellOption long id) {
        System.out.println(managerBook.findById(id));
    }

    @ShellMethod(value = "Add comment", key = "comment")
    public void addComment(@ShellOption String username, @ShellOption long bookId, @ShellOption String message) {
        Optional<Book> optionalBook = managerBook.findById(bookId);
        if (optionalBook.isEmpty())
            throw new RuntimeException("ATTENTION BOOK NOT FOUND!!!!!ONE!!!");
        Book book = optionalBook.get();
        Comment object = new Comment();
        object.setBook(book);
        book.getComments().add(object);
        object.setUsername(username);
        object.setMessage(message);
        managerComment.save(object);
        System.out.println(object);
    }

    @ShellMethod(value = "Get comments", key = "get-comments")
    public void getComments(@ShellOption long bookId) {
        Optional<Book> optionalBook = managerBook.findById(bookId);
        if (optionalBook.isEmpty())
            throw new RuntimeException("ATTENTION BOOK NOT FOUND!!!!!ONE!!!");
        List<Comment> comments = optionalBook.get().getComments();
        for (Comment comment : comments) {
            System.out.printf("%s: %s%n", comment.getUsername(), comment.getMessage());
        }
    }
}
