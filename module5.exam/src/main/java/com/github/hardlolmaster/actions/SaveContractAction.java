package com.github.hardlolmaster.actions;

import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import com.github.hardlolmaster.repository.ContractRepository;
import com.github.hardlolmaster.utils.Validator;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.github.hardlolmaster.controller.CommonResponseObject.INCORRECT_INPUT;
import static com.github.hardlolmaster.controller.CommonResponseObject.SOMETHING_WENT_WRONG;
import static com.github.hardlolmaster.utils.GetCommandGroupKey.getFor;

@Component
public class SaveContractAction extends AbstractAction
{
    private final ContractRepository repository;
    private final Validator validator;

    @Autowired
    public SaveContractAction(ContractRepository repository,
            @Qualifier("saveContractValidator") Validator validator)
    {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public ResponseObject<?> execute(Object input)
    {
        if (!(input instanceof PropertyInsuranceContract))
        {
            return new ResponseObject<>(false, "Получен объект не того типа");
        }
        final PropertyInsuranceContract contract = (PropertyInsuranceContract) input;
        if (!validator.valid(contract))
            return INCORRECT_INPUT;
        //TODO fix bug with timeout. Command fail in debug by timeout, default timeout is 1s
        HystrixCommand.Setter config = HystrixCommand
                .Setter
                .withGroupKey(getFor("SaveContract"));
        HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties.Setter();
        commandProperties.withExecutionTimeoutInMilliseconds(30_000);
        config.andCommandPropertiesDefaults(commandProperties);

        return new HystrixCommand<ResponseObject<?>>(config)
        {
            @Override
            protected ResponseObject<?> run()
            {
                if ((contract.getId() == null && !repository.existsByNumber(contract.getNumber())))
                    return new ResponseObject<>(true, repository.save(contract));

                Optional<PropertyInsuranceContract> byNumber = repository.findByNumber(contract.getNumber());
                if (byNumber.isEmpty())
                    throw new RuntimeException("Contract with number " + contract.getNumber() + " not existing");

                if (contract.getNumber().equals(byNumber.get().getNumber()))
                    return new ResponseObject<>(true, repository.save(contract));
                else if (!repository.existsByNumber(contract.getNumber()))
                    return new ResponseObject<>(true, repository.save(contract));

                throw new RuntimeException("Can't save the contract. Contract with number " + contract.getNumber() + " already existing");
            }

            @Override
            protected ResponseObject<?> getFallback()
            {
                return SOMETHING_WENT_WRONG;
            }

        }.execute();
    }
}
