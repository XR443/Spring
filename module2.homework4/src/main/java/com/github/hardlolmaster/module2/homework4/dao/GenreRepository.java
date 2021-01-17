package com.github.hardlolmaster.module2.homework4.dao;

import com.github.hardlolmaster.module2.homework4.domain.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface GenreRepository extends MongoRepository<Genre, BigInteger> {
    Genre findByName(String name);
}
