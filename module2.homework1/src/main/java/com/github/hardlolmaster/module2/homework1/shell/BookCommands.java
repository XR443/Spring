package com.github.hardlolmaster.module2.homework1.shell;

import com.github.hardlolmaster.module2.homework1.domain.Book;
import com.github.hardlolmaster.module2.homework1.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class BookCommands {
    private final BookDao bookDao;
    private Book book;

    @Autowired
    public BookCommands(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @ShellMethod(value = "Insert book", key = "insert-book")
    public void insertBook(@ShellOption long id,
                           @ShellOption String name) {
        bookDao.insert(new Book(id, name, GenreCommands.getGenre().getId(), AuthorCommands.getAuthor().getId()));
    }

    @ShellMethod(value = "Get all books", key = "get-all-book")
    public void getAllBooks() {
        System.out.println(bookDao.findAll());
    }

    @ShellMethod(value = "Get book", key = "get-book")
    public void getBookById(@ShellOption long id) {
        System.out.println(bookDao.findById(id));
    }

    @ShellMethod(value = "Store book", key = "store-book")
    public void storeBookById(@ShellOption long id) {
        book = bookDao.findById(id);
    }

    public Book getBook() {
        return book;
    }
}
