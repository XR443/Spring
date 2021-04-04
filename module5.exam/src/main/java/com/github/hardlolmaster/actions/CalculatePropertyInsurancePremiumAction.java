package com.github.hardlolmaster.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import com.github.hardlolmaster.hystrix.CalculatePropertyInsurancePremiumCommand;
import com.github.hardlolmaster.service.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalculatePropertyInsurancePremiumAction extends AbstractAction {
    private final CalculateService service;
    private final ObjectMapper objectMapper;

    @Autowired
    public CalculatePropertyInsurancePremiumAction(CalculateService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseObject<PropertyInsuranceContract> execute(Object input) {
        PropertyInsuranceContract contract = objectMapper.convertValue(input, PropertyInsuranceContract.class);
        return new CalculatePropertyInsurancePremiumCommand(service, contract).execute();
    }
}
