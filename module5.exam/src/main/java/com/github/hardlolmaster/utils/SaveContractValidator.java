package com.github.hardlolmaster.utils;

import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SaveContractValidator implements Validator {
    private final Validator insuranceObjectValidator;
    private final Validator individualValidator;

    @Autowired
    public SaveContractValidator(@Qualifier("insuranceObjectValidator") Validator insuranceObjectValidator,
                                 @Qualifier("individualValidator") Validator individualValidator) {
        this.insuranceObjectValidator = insuranceObjectValidator;
        this.individualValidator = individualValidator;
    }

    @Override
    public boolean valid(Object object) {
        if (!(object instanceof PropertyInsuranceContract))
            return false;

        PropertyInsuranceContract contract = (PropertyInsuranceContract) object;
        if (contract.getInsurancePeriodFrom() == null)
            return false;
        if (contract.getInsurancePeriodTo() == null)
            return false;
        if (contract.getCalculateDate() == null)
            return false;
        if (contract.getConclusionDate() == null)
            return false;
        if (!contract.getInsurancePeriodFrom().before(contract.getInsurancePeriodTo()))
            return false;

        if (!insuranceObjectValidator.valid(contract.getInsuranceObject()))
            return false;

        return individualValidator.valid(contract.getInsurer());
    }
}
