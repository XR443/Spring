package com.github.hardlolmaster.hystrix;

import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import com.github.hardlolmaster.service.CalculateService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.sun.istack.NotNull;

public class CalculatePropertyInsurancePremiumCommand extends HystrixCommand<ResponseObject<PropertyInsuranceContract>> {
    private final CalculateService service;
    private final PropertyInsuranceContract contract;

    public CalculatePropertyInsurancePremiumCommand(CalculateService service, @NotNull PropertyInsuranceContract contract) {
        super(HystrixCommandGroupKey.Factory.asKey(CalculatePropertyInsurancePremiumCommand.class.getSimpleName()));
        this.service = service;
        this.contract = contract;
    }

    @Override
    protected ResponseObject<PropertyInsuranceContract> run() {
        return new ResponseObject<>(true, service.calculate(contract));
    }

    @Override
    protected ResponseObject<PropertyInsuranceContract> getFallback() {
        return new ResponseObject<>(false, null);
    }
}
