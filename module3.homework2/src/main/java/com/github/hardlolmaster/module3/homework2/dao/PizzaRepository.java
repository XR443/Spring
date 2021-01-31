package com.github.hardlolmaster.module3.homework2.dao;


import com.github.hardlolmaster.module3.homework2.domain.Pizza;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PizzaRepository extends CrudRepository<Pizza, Long> {
    List<Pizza> findAll();
}
