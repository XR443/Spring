package com.github.hardlolmaster.module2.homework1.dao;

import com.github.hardlolmaster.module2.homework1.domain.Author;
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
public class AuthorDao implements IDao<Author> {
    private final NamedParameterJdbcOperations jdbc;
    private final AuthorMapper mapper = new AuthorMapper();

    @Autowired
    public AuthorDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Author findById(Long id) {
        Map<String, Object> params = singletonMap("id", id);
        return jdbc.queryForObject("select * from authors where id = :id", params, mapper);
    }

    @Override
    public void insert(Author obj) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("id", obj.getId());
        params.put("name", obj.getName());
        params.put("lastname", obj.getLastName());
        jdbc.update("insert into authors (id, name, lastname) values (:id, :name, :lastname)", params);
    }

    @Override
    public void update(Author obj) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("id", obj.getId());
        params.put("name", obj.getName());
        params.put("lastname", obj.getLastName());
        jdbc.update("update authors set name = :name, lastname =:lastname, where id = :id", params);
    }

    @Override
    public void deleteById(Long id) {
        Map<String, Object> params = singletonMap("id", id);
        jdbc.update("delete from authors where id = :id", params);
    }

    @Override
    public List<Author> findAll() {
        return jdbc.query("select * from authors", mapper);
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String lastname = rs.getString("lastname");
            return new Author(id, name, lastname);
        }
    }
}
