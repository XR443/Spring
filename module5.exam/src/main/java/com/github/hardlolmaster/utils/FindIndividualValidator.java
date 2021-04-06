package com.github.hardlolmaster.utils;

import com.github.hardlolmaster.domain.Individual;
import org.springframework.stereotype.Component;

@Component
public class FindIndividualValidator implements Validator {

    @Override
    public boolean valid(Object object) {
        if (!(object instanceof Individual))
            return false;
        Individual individual = (Individual) object;
        if (individual.getFirstName() == null || individual.getFirstName().isEmpty())
            return false;
        return individual.getLastName() != null && !individual.getLastName().isEmpty();
    }
}
