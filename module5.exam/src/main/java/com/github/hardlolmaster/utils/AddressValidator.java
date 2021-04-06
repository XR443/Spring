package com.github.hardlolmaster.utils;

import com.github.hardlolmaster.domain.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressValidator implements Validator {
    @Override
    public boolean valid(Object object) {
        if (!(object instanceof Address))
            return false;
        Address address = (Address) object;
        if (address.getCountry() == null || address.getCountry().isEmpty())
            return false;
        if (address.getRegion() == null || address.getRegion().isEmpty())
            return false;
        if (address.getCity() == null || address.getCity().isEmpty())
            return false;
        if (address.getStreet() == null || address.getStreet().isEmpty())
            return false;
        return address.getApartment() != null;
    }
}
