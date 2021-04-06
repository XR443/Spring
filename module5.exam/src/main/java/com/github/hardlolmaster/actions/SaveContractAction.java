package com.github.hardlolmaster.actions;

import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import com.github.hardlolmaster.repository.ContractRepository;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.github.hardlolmaster.controller.CommonResponseObject.SOMETHING_WENT_WRONG;
import static com.github.hardlolmaster.utils.GetCommandGroupKey.getFor;

@Component
public class SaveContractAction extends AbstractAction {
    private final ContractRepository repository;

    @Autowired
    public SaveContractAction(ContractRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseObject<?> execute(Object input) {
        if (!(input instanceof PropertyInsuranceContract)) {
            return new ResponseObject<>(false, "Получен объект не того типа");
        }
        final PropertyInsuranceContract contract = (PropertyInsuranceContract) input;
        return new HystrixCommand<ResponseObject<?>>(getFor("SaveContract")) {
            @Override
            protected ResponseObject<?> run() {
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
            protected ResponseObject<?> getFallback() {
                return SOMETHING_WENT_WRONG;
            }

        }.execute();
    }
}
