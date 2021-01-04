package com.github.hardlolmaster.module2.homework1.shell;

import com.github.hardlolmaster.module2.homework1.domain.Author;
import com.github.hardlolmaster.module2.homework1.dao.AuthorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class AuthorCommands {
    private static Author author;
    private final AuthorDao authorDao;

    @Autowired
    public AuthorCommands(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public static Author getAuthor() {
        return author;
    }

    @ShellMethod(value = "Insert author in db", key = "insert-author")
    public void insertAuthor(@ShellOption long id,
                             @ShellOption String name,
                             @ShellOption String lastName) {
        authorDao.insert(new Author(id, name, lastName));
        storeAuthorById(id);
    }

    @ShellMethod(value = "Get all authors from db", key = "get-all-authors")
    public void getAllAuthors() {
        System.out.println(authorDao.findAll());
    }

    @ShellMethod(value = "Get author from db", key = "get-author")
    public void getAuthorById(@ShellOption long id) {
        System.out.println(authorDao.findById(id));
    }

    @ShellMethod(value = "Store author", key = "store-author")
    public void storeAuthorById(@ShellOption long id) {
        author = authorDao.findById(id);
    }
}
