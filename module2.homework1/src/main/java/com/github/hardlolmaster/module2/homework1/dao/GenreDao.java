package com.github.hardlolmaster.module2.homework1.dao;

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
public class GenreDao implements IDao<Genre> {
    private final NamedParameterJdbcOperations jdbc;
    private final GenreMapper mapper = new GenreMapper();

    @Autowired
    public GenreDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Genre findById(Long id) {
        Map<String, Object> params = singletonMap("id", id);
        return jdbc.queryForObject("select * from genres where id = :id", params, mapper);
    }

    @Override
    public void insert(Genre obj) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("id", obj.getId());
        params.put("name", obj.getName());
        jdbc.update("insert into genres (id, name) values (:id, :name)", params);
    }

    @Override
    public void update(Genre obj) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("id", obj.getId());
        params.put("name", obj.getName());
        jdbc.update("update genres set name = :name where id = :id", params);
    }

    @Override
    public void deleteById(Long id) {
        Map<String, Object> params = singletonMap("id", id);
        jdbc.update("delete from genres where id = :id", params);
    }

    @Override
    public List<Genre> findAll() {
        return jdbc.query("select * from genres", mapper);
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Genre(id, name);
        }
    }
}
