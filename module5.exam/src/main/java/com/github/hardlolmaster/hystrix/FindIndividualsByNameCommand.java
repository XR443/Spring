package com.github.hardlolmaster.hystrix;

import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.Individual;
import com.github.hardlolmaster.repository.IndividualRepository;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FindIndividualsByNameCommand extends HystrixCommand<ResponseObject<List<Individual>>> {

    private final IndividualRepository repository;
    private final Individual individual;

    public FindIndividualsByNameCommand(IndividualRepository repository, @NotNull Individual individual) {
        super(HystrixCommandGroupKey.Factory.asKey(FindIndividualsByNameCommand.class.getSimpleName()));
        this.repository = repository;
        this.individual = individual;
    }

    @Override
    protected ResponseObject<List<Individual>> run() {
        List<Individual> result;
        if (individual.getSecondName() == null || individual.getSecondName().isEmpty())
            result = repository
                    .findByFirstNameIsLikeIgnoreCaseAndLastNameIsLikeIgnoreCase(
                            prepareString(individual.getFirstName()),
                            prepareString(individual.getLastName()));
        else
            result = repository
                    .findByFirstNameIsLikeIgnoreCaseAndLastNameIsLikeIgnoreCaseAndSecondNameIsLikeIgnoreCase(
                            prepareString(individual.getFirstName()),
                            prepareString(individual.getLastName()),
                            prepareString(individual.getSecondName()));
        return new ResponseObject<>(true, result);
    }

    @Override
    protected ResponseObject<List<Individual>> getFallback() {
        return new ResponseObject<>(false, new ArrayList<>());
    }

    private String prepareString(String input) {
        return "%" + input + "%";
    }
}
