package com.github.hardlolmaster.hystrix;

import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import com.github.hardlolmaster.repository.ContractRepository;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.ArrayList;
import java.util.List;

public class GetAllContractsCommand extends HystrixCommand<ResponseObject<List<PropertyInsuranceContract>>> {
    private final ContractRepository repository;

    public GetAllContractsCommand(ContractRepository repository) {
        super(HystrixCommandGroupKey.Factory.asKey(GetAllContractsCommand.class.getSimpleName()));
        this.repository = repository;
    }

    @Override
    protected ResponseObject<List<PropertyInsuranceContract>> run() {
        return new ResponseObject<>(true, repository.findAll());
    }

    @Override
    protected ResponseObject<List<PropertyInsuranceContract>> getFallback() {
        return new ResponseObject<>(false, new ArrayList<>());
    }
}
