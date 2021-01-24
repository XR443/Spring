package com.github.hardlolmaster.module3.homework1.dao;

import com.github.hardlolmaster.module3.homework1.domain.Pizza;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PizzaRepository extends CrudRepository<Pizza, Long> {
    List<Pizza> findAll();
}
