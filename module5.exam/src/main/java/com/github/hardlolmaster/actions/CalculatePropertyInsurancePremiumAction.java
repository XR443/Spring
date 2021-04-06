package com.github.hardlolmaster.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import com.github.hardlolmaster.service.CalculateService;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.hardlolmaster.controller.CommonResponseObject.SOMETHING_WENT_WRONG;
import static com.github.hardlolmaster.utils.GetCommandGroupKey.getFor;

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
    public ResponseObject<?> execute(Object input) {
        PropertyInsuranceContract contract = objectMapper.convertValue(input, PropertyInsuranceContract.class);
        return new HystrixCommand<ResponseObject<?>>(getFor("CalculatePropertyInsurancePremium")) {

            @Override
            protected ResponseObject<?> run() {
                return new ResponseObject<>(true, service.calculate(contract));
            }

            @Override
            protected ResponseObject<?> getFallback() {
                return SOMETHING_WENT_WRONG;
            }

        }.execute();
    }
}
