package com.github.hardlolmaster.module2.homework4.dao;

import com.github.hardlolmaster.module2.homework4.domain.Author;
import com.github.hardlolmaster.module2.homework4.domain.Book;
import com.github.hardlolmaster.module2.homework4.domain.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;

public interface BookRepository extends MongoRepository<Book, BigInteger> {
    Book findByName(String name);

    List<Book> findBooksByAuthor(Author author);

    List<Book> findBooksByGenre(Genre genre);
}
