package com.github.hardlolmaster.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.Individual;
import com.github.hardlolmaster.hystrix.FindIndividualsByNameCommand;
import com.github.hardlolmaster.repository.IndividualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindIndividualsByNameAction extends AbstractAction {
    private final IndividualRepository repository;
    private final ObjectMapper objectMapper;

    @Autowired
    public FindIndividualsByNameAction(IndividualRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseObject<List<Individual>> execute(Object input) {
        Individual individual = objectMapper.convertValue(input, Individual.class);
        return new FindIndividualsByNameCommand(repository, individual).execute();
    }
}
