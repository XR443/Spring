package com.github.hardlolmaster.module3.homework1.dao;

import com.github.hardlolmaster.module3.homework1.domain.PizzaAddition;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PizzaAdditionRepository extends CrudRepository<PizzaAddition, Long> {
    List<PizzaAddition> findAll();
    List<PizzaAddition> findAllById(Iterable<Long> ids);
}
