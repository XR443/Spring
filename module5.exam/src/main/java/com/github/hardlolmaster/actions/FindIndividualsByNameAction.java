package com.github.hardlolmaster.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.Individual;
import com.github.hardlolmaster.repository.IndividualRepository;
import com.github.hardlolmaster.utils.Validator;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.github.hardlolmaster.controller.CommonResponseObject.SOMETHING_WENT_WRONG;
import static com.github.hardlolmaster.utils.GetCommandGroupKey.getFor;

@Component
public class FindIndividualsByNameAction extends AbstractAction {
    private final IndividualRepository repository;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    @Autowired
    public FindIndividualsByNameAction(IndividualRepository repository,
                                       ObjectMapper objectMapper,
                                       @Qualifier("findIndividualValidator") Validator validator) {
        this.repository = repository;
        this.objectMapper = objectMapper;
        this.validator = validator;
    }

    @Override
    public ResponseObject<?> execute(Object input) {
        Individual individual = objectMapper.convertValue(input, Individual.class);
        if (!validator.valid(individual))
            return new ResponseObject<>(false, "Указаны некорректные данные");

        return new HystrixCommand<ResponseObject<?>>(getFor("FindIndividualsByName")) {

            @Override
            protected ResponseObject<?> run() {
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
            protected ResponseObject<?> getFallback() {
                return SOMETHING_WENT_WRONG;
            }

            private String prepareString(String input) {
                return "%" + input + "%";
            }

        }.execute();
    }
}
