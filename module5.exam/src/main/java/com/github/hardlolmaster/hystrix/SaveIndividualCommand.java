package com.github.hardlolmaster.hystrix;

import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.Individual;
import com.github.hardlolmaster.repository.IndividualRepository;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class SaveIndividualCommand extends HystrixCommand<ResponseObject<Individual>> {
    private final IndividualRepository repository;
    private final Individual individual;

    public SaveIndividualCommand(IndividualRepository repository, Individual individual) {
        super(HystrixCommandGroupKey.Factory.asKey(SaveIndividualCommand.class.getSimpleName()));
        this.repository = repository;
        this.individual = individual;
    }

    @Override
    protected ResponseObject<Individual> run() throws Exception {
        return new ResponseObject<>(true, repository.save(individual));
    }

    @Override
    protected ResponseObject<Individual> getFallback() {
        return new ResponseObject<>(false, null);
    }
}
