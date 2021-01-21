package com.github.hardlolmaster.module2.homework2.dao;

import com.github.hardlolmaster.module2.homework2.domain.Author;
import com.github.hardlolmaster.module2.homework2.domain.Book;
import com.github.hardlolmaster.module2.homework2.domain.Genre;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PersistenceManagerBookImpl implements IPersistenceManagerBook {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Book object) {
        entityManager.persist(object);
        entityManager.flush();
    }

    @Override
    public Book getByID(Long id) {
        Book book = entityManager.find(Book.class, id);
        return book;
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> namedQuery = entityManager.createQuery("select b from Book b", Book.class);
        return namedQuery.getResultList();
    }

    @Override
    public Book getByName(String name) {
        TypedQuery<Book> namedQuery = entityManager.createQuery("select b from Book b where b.name = :name", Book.class);
        namedQuery.setParameter("name", name);
        return namedQuery.getSingleResult();
    }

    @Override
    public List<Book> getBooksByGenre(Genre genre) {
        TypedQuery<Book> namedQuery = entityManager.createQuery("select b from Book b where b.genre = :genre", Book.class);
        namedQuery.setParameter("genre", genre);
        return namedQuery.getResultList();
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        TypedQuery<Book> namedQuery = entityManager.createQuery("select b from Book b where b.author = :author", Book.class);
        namedQuery.setParameter("author", author);
        return namedQuery.getResultList();
    }
}
