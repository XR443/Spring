package com.github.hardlolmaster.module3.homework2.dao;

import com.github.hardlolmaster.module3.homework2.domain.PizzaType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PizzaTypeRepository extends CrudRepository<PizzaType, Long> {
    List<PizzaType> findAll();
}
