package com.github.hardlolmaster.module2.homework3.dao;

import com.github.hardlolmaster.module2.homework3.domain.Author;
import com.github.hardlolmaster.module2.homework3.domain.Book;
import com.github.hardlolmaster.module2.homework3.domain.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPersistenceManagerBook extends CrudRepository<Book, Long> {
    Book findByName(String name);
    List<Book> findBooksByGenre(Genre genre);
    List<Book> findBooksByAuthor(Author author);
}
