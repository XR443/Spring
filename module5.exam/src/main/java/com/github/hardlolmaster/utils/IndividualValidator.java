package com.github.hardlolmaster.utils;

import com.github.hardlolmaster.domain.Individual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class IndividualValidator implements Validator {
    private final Validator documentValidator;

    @Autowired
    public IndividualValidator(@Qualifier("documentValidator") Validator documentValidator) {
        this.documentValidator = documentValidator;
    }

    @Override
    public boolean valid(Object object) {
        if (!(object instanceof Individual))
            return false;
        Individual individual = (Individual) object;
        if (individual.getFirstName() == null || individual.getFirstName().isEmpty())
            return false;
        if (individual.getLastName() == null || individual.getLastName().isEmpty())
            return false;
        if (individual.getBirthdate() == null)
            return false;
        return documentValidator.valid(individual.getDocument());
    }
}
