package com.github.hardlolmaster.utils;

import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import com.github.hardlolmaster.domain.PropertyInsuranceObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CalcContractValidator implements Validator
{

    @Override public boolean valid(Object object)
    {
        if (object == null)
            return false;

        if (!(object instanceof PropertyInsuranceContract))
            return false;

        PropertyInsuranceContract contract = (PropertyInsuranceContract) object;

        Date insurancePeriodFrom = contract.getInsurancePeriodFrom();
        Date insurancePeriodTo = contract.getInsurancePeriodTo();
        if (insurancePeriodFrom == null || insurancePeriodTo == null)
            return false;
        if (!insurancePeriodFrom.before(insurancePeriodTo))
            return false;

        PropertyInsuranceObject insuranceObject = contract.getInsuranceObject();

        if (insuranceObject.getInsuranceSum() == null || insuranceObject.getInsuranceSum() <= 0.0d)
            return false;
        if (insuranceObject.getPropertyType() == null || insuranceObject.getPropertyType().isEmpty())
            return false;
        if (insuranceObject.getConstructionYear() == null)
            return false;
        return insuranceObject.getArea() != null && !(insuranceObject.getArea() <= 0.0d);
    }
}
