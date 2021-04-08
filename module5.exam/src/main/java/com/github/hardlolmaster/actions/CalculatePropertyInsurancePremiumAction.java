package com.github.hardlolmaster.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import com.github.hardlolmaster.service.CalculateService;
import com.github.hardlolmaster.utils.Validator;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.github.hardlolmaster.controller.CommonResponseObject.INCORRECT_INPUT;
import static com.github.hardlolmaster.controller.CommonResponseObject.SOMETHING_WENT_WRONG;
import static com.github.hardlolmaster.utils.GetCommandGroupKey.getFor;

@Component
public class CalculatePropertyInsurancePremiumAction extends AbstractAction
{
    private final CalculateService service;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    @Autowired
    public CalculatePropertyInsurancePremiumAction(CalculateService service,
            ObjectMapper objectMapper,
            @Qualifier("calcContractValidator") Validator validator)
    {
        this.service = service;
        this.objectMapper = objectMapper;
        this.validator = validator;
    }

    @Override
    public ResponseObject<?> execute(Object input)
    {
        PropertyInsuranceContract contract = objectMapper.convertValue(input, PropertyInsuranceContract.class);
        if(!validator.valid(contract))
            return INCORRECT_INPUT;
        return new HystrixCommand<ResponseObject<?>>(getFor("CalculatePropertyInsurancePremium"))
        {

            @Override
            protected ResponseObject<?> run()
            {
                return new ResponseObject<>(true, service.calculate(contract));
            }

            @Override
            protected ResponseObject<?> getFallback()
            {
                return SOMETHING_WENT_WRONG;
            }

        }.execute();
    }
}
