package com.github.hardlolmaster.utils;

import com.github.hardlolmaster.domain.PropertyInsuranceObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class InsuranceObjectValidator implements Validator {
    private final Validator addressValidator;

    @Autowired
    public InsuranceObjectValidator(@Qualifier("addressValidator") Validator addressValidator) {
        this.addressValidator = addressValidator;
    }

    @Override
    public boolean valid(Object object) {
        if (!(object instanceof PropertyInsuranceObject))
            return false;
        PropertyInsuranceObject insuranceObject = (PropertyInsuranceObject) object;
        if (insuranceObject.getInsuranceSum() == null || insuranceObject.getInsuranceSum() > 0.0d)
            return false;
        if (insuranceObject.getInsurancePremium() == null || insuranceObject.getInsurancePremium() > 0.0d)
            return false;
        if (insuranceObject.getPropertyType() == null || insuranceObject.getPropertyType().isEmpty())
            return false;
        if (insuranceObject.getConstructionYear() == null)
            return false;
        if (insuranceObject.getArea() == null || insuranceObject.getArea() > 0.0d)
            return false;
        return addressValidator.valid(insuranceObject.getAddress());
    }
}
