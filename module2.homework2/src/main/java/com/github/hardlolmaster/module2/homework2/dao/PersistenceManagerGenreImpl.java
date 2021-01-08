package com.github.hardlolmaster.module2.homework2.dao;

import com.github.hardlolmaster.module2.homework2.domain.Genre;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Repository
@Transactional
public class PersistenceManagerGenreImpl implements IPersistenceManagerGenre {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Genre genre) {
        entityManager.persist(genre);
        entityManager.flush();
    }

    @Override
    public Genre getByID(Long id) {
        return entityManager.find(Genre.class, id);
    }

    @Override
    public Genre getByName(String name) {
        TypedQuery<Genre> namedQuery = entityManager.createQuery("select g from Genre g where g.name = :name", Genre.class);
        namedQuery.setParameter("name", name);
        return namedQuery.getSingleResult();
    }
}
