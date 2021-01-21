package com.github.hardlolmaster.module2.homework2.dao;

import com.github.hardlolmaster.module2.homework2.domain.Author;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PersistenceManagerAuthorImpl implements IPersistenceManagerAuthor {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Author object) {
        entityManager.persist(object);
        entityManager.flush();
    }

    @Override
    public Author getByID(Long id) {
        return entityManager.find(Author.class, id);
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> namedQuery = entityManager.createQuery("select a from Author a", Author.class);
        List<Author> resultList = namedQuery.getResultList();
        return resultList;
    }

    @Override
    public Author getByName(String name, String lastName) {
        TypedQuery<Author> namedQuery = entityManager.createQuery("select a from Author a where a.name = :name and a.lastName = :lastName",
                Author.class);
        namedQuery.setParameter("name", name);
        namedQuery.setParameter("lastName", lastName);
        return namedQuery.getSingleResult();
    }
}
