package com.github.hardlolmaster.repository;

import com.github.hardlolmaster.domain.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostFilter;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    @PostFilter("hasPermission(filterObject, 'READ')")
    List<Post> findAll();
}
