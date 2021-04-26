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

import static com.github.hardlolmaster.controller.CommonResponseObject.INCORRECT_INPUT;
import static com.github.hardlolmaster.utils.GetCommandGroupKey.getFor;

@Component
public class SaveIndividualNoDocAction extends AbstractAction
{
    private final IndividualRepository repository;
    private final Validator validator;
    private final ObjectMapper objectMapper;

    @Autowired
    public SaveIndividualNoDocAction(IndividualRepository repository,
            @Qualifier("individualValidatorNoDocument") Validator validator,
            ObjectMapper objectMapper)
    {
        this.repository = repository;
        this.validator = validator;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseObject<?> execute(Object input)
    {
        final Individual individual = objectMapper.convertValue(input, Individual.class);
        if (!validator.valid(individual))
            return INCORRECT_INPUT;
        return new HystrixCommand<ResponseObject<?>>(getFor("SaveContract"))
        {
            @Override
            protected ResponseObject<?> run()
            {
                return new ResponseObject<>(true, repository.save(individual));
            }

        }.execute();
    }
}
