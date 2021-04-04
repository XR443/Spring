package com.github.hardlolmaster.actions;

import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.hystrix.GetAllContractsCommand;
import com.github.hardlolmaster.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAllContractsAction extends AbstractAction {
    private final ContractRepository repository;

    @Autowired
    public GetAllContractsAction(ContractRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseObject<?> execute(Object input) {
        return new GetAllContractsCommand(repository).execute();
    }
}
