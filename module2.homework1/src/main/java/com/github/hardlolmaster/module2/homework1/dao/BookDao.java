package com.github.hardlolmaster.module2.homework1.dao;

import com.github.hardlolmaster.module2.homework1.domain.Author;
import com.github.hardlolmaster.module2.homework1.domain.Book;
import com.github.hardlolmaster.module2.homework1.domain.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;

@Repository
public class BookDao implements IDao<Book> {
    private final NamedParameterJdbcOperations jdbc;
    private final BookMapper mapper = new BookMapper();

    @Autowired
    public BookDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Book findById(Long id) {
        Map<String, Object> params = singletonMap("id", id);
        return jdbc.queryForObject("select * from books where id = :id", params, mapper);
    }

    @Override
    public void insert(Book obj) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("id", obj.getId());
        params.put("name", obj.getName());
        params.put("genre", obj.getGenre());
        params.put("author", obj.getAuthor());
        jdbc.update("insert into books (id, name, genre, author) values (:id, :name, :genre, :author)", params);
    }

    @Override
    public void update(Book obj) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("id", obj.getId());
        params.put("name", obj.getName());
        params.put("genre", obj.getGenre());
        params.put("author", obj.getAuthor());
        jdbc.update("update books set name = :name, genre = :genre, author = :author where id = :id", params);
    }

    @Override
    public void deleteById(Long id) {
        Map<String, Object> params = singletonMap("id", id);
        jdbc.update("delete from books where id = :id", params);
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("select * from books", mapper);
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            Long genre = (Long) rs.getObject("genre");
            Long author = (Long) rs.getObject("author");
            return new Book(id, name, genre, author);
        }
    }
}
