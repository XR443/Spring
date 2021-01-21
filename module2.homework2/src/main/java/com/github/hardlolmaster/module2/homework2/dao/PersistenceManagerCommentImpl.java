package com.github.hardlolmaster.module2.homework2.dao;

import com.github.hardlolmaster.module2.homework2.domain.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PersistenceManagerCommentImpl implements IPersistenceManagerComment {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Comment object) {
        entityManager.persist(object);
        entityManager.merge(object.getBook());
        entityManager.flush();
    }

    @Override
    public Comment getByID(Long id) {
        return entityManager.find(Comment.class, id);
    }

    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> namedQuery = entityManager.createQuery("select b from Comment b", Comment.class);
        return namedQuery.getResultList();
    }

}
