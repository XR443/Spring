package com.github.hardlolmaster.actions;

import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.Individual;
import com.github.hardlolmaster.repository.IndividualRepository;
import com.github.hardlolmaster.utils.Validator;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.github.hardlolmaster.controller.CommonResponseObject.INCORRECT_INPUT;
import static com.github.hardlolmaster.controller.CommonResponseObject.SOMETHING_WENT_WRONG;
import static com.github.hardlolmaster.utils.GetCommandGroupKey.getFor;

@Component
public class SaveIndividualAction extends AbstractAction {
    private final IndividualRepository repository;
    private final Validator validator;

    @Autowired
    public SaveIndividualAction(IndividualRepository repository,
            @Qualifier("individualValidator") Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public ResponseObject<?> execute(Object input) {
        if (!(input instanceof Individual)) {
            return new ResponseObject<>(false, "Получен объект не того типа");
        }
        final Individual individual = (Individual) input;
        if(validator.valid(individual))
            return INCORRECT_INPUT;
        return new HystrixCommand<ResponseObject<?>>(getFor("SaveContract")) {
            @Override
            protected ResponseObject<?> run() {
                return new ResponseObject<>(true, repository.save(individual));
            }

            @Override
            protected ResponseObject<?> getFallback() {
                return SOMETHING_WENT_WRONG;
            }

        }.execute();
    }
}
