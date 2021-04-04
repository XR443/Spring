package com.github.hardlolmaster.hystrix;

import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import com.github.hardlolmaster.repository.ContractRepository;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.sun.istack.NotNull;

public class SavePropertyInsuranceContract extends HystrixCommand<ResponseObject<PropertyInsuranceContract>> {
    private final ContractRepository repository;
    private final PropertyInsuranceContract contract;

    public SavePropertyInsuranceContract(ContractRepository repository, @NotNull PropertyInsuranceContract contract) {
        super(HystrixCommandGroupKey.Factory.asKey(SavePropertyInsuranceContract.class.getSimpleName()));
        this.repository = repository;
        this.contract = contract;
    }

    @Override
    protected ResponseObject<PropertyInsuranceContract> run() throws Exception {
        PropertyInsuranceContract saved = repository.save(contract);
        return new ResponseObject<>(true, saved);
    }

    @Override
    protected ResponseObject<PropertyInsuranceContract> getFallback() {
        return new ResponseObject<>(false, null);
    }
}
