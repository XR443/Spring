package com.github.hardlolmaster.module2.homework4.dao;

import com.github.hardlolmaster.module2.homework4.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface AuthorRepository extends MongoRepository<Author, BigInteger> {
    Author findByNameAndLastName(String name, String lastName);
}
