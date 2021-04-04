package com.github.hardlolmaster.repository;

import com.github.hardlolmaster.domain.Individual;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IndividualRepository extends CrudRepository<Individual, Long> {
    List<Individual> findByFirstNameIsLikeIgnoreCaseAndLastNameIsLikeIgnoreCase(String firstName, String lastName);

    List<Individual> findByFirstNameIsLikeIgnoreCaseAndLastNameIsLikeIgnoreCaseAndSecondNameIsLikeIgnoreCase(String firstName,
                                                                                                             String lastName,
                                                                                                             String secondName);
}
