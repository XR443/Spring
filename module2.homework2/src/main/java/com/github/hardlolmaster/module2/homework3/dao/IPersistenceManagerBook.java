package com.github.hardlolmaster.module2.homework3.dao;

import com.github.hardlolmaster.module2.homework3.domain.Author;
import com.github.hardlolmaster.module2.homework3.domain.Book;
import com.github.hardlolmaster.module2.homework3.domain.Genre;

import java.util.List;

public interface IPersistenceManagerBook extends IPersistenceManager<Book> {
    Book getByName(String name);
    List<Book> getBooksByGenre(Genre genre);
    List<Book> getBooksByAuthor(Author author);
}
