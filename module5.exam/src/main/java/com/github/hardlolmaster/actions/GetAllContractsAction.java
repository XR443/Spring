package com.github.hardlolmaster.actions;

import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.repository.ContractRepository;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.hardlolmaster.controller.CommonResponseObject.SOMETHING_WENT_WRONG;
import static com.github.hardlolmaster.utils.GetCommandGroupKey.getFor;

@Component
public class GetAllContractsAction extends AbstractAction
{
    private final ContractRepository repository;

    @Autowired
    public GetAllContractsAction(ContractRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public ResponseObject<?> execute(Object input)
    {
        return new ResponseObject<>(true, repository.findAll());
    }
}
