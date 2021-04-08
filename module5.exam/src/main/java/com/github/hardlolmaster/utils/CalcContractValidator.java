package com.github.hardlolmaster.utils;

import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import com.github.hardlolmaster.domain.PropertyInsuranceObject;
import org.springframework.stereotype.Component;

@Component
public class CalcContractValidator implements Validator
{

    @Override public boolean valid(Object object)
    {
        if (!(object instanceof PropertyInsuranceContract))
            return false;

        PropertyInsuranceContract contract = (PropertyInsuranceContract) object;

        if (!contract.getInsurancePeriodFrom().before(contract.getInsurancePeriodTo()))
            return false;

        PropertyInsuranceObject insuranceObject = contract.getInsuranceObject();

        if (insuranceObject.getInsuranceSum() == null || insuranceObject.getInsuranceSum() <= 0.0d)
            return false;
        if (insuranceObject.getInsurancePremium() != null && insuranceObject.getInsurancePremium() > 0.0d)
            return false;
        if (insuranceObject.getPropertyType() == null || insuranceObject.getPropertyType().isEmpty())
            return false;
        if (insuranceObject.getConstructionYear() == null)
            return false;
        return insuranceObject.getArea() != null && !(insuranceObject.getArea() <= 0.0d);
    }
}
