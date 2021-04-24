package com.github.hardlolmaster;

import com.github.hardlolmaster.domain.*;
import com.github.hardlolmaster.service.CalculateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculateTest {
    @Autowired
    private CalculateService calculateService;

    @Test
    public void testCalculate() {
        PropertyInsuranceContract contract = new PropertyInsuranceContract();
        contract.setInsurancePeriodFrom(getDate(2021, 1, 1));
        contract.setInsurancePeriodTo(getDate(2022, 2, 2));
        contract.setCalculateDate(getDate(2021, 1, 1));
        contract.setConclusionDate(getDate(2021, 1, 1));
        contract.setNumber("11111111");
        Individual insurer = new Individual();
        insurer.setFirstName("F");
        insurer.setLastName("F");
        insurer.setSecondName("F");
        insurer.setBirthday(getDate(2000,1,1));
        Document document = new Document();
        document.setNumber("111111");
        document.setSeries("1111");
        document.setIssuedWhen(getDate(2020,1,1));
        document.setIssuedBy("1111111");
        insurer.setDocument(document);
        contract.setInsurer(insurer);
        PropertyInsuranceObject insuranceObject = new PropertyInsuranceObject();
        insuranceObject.setInsuranceSum(100_000d);
        insuranceObject.setPropertyType("Квартира");
        insuranceObject.setArea(50d);
        insuranceObject.setConstructionYear(2015L);
        Address address = new Address();
        address.setApartment(11L);
        address.setCity("city");
        address.setCountry("Country");
        address.setHouse(10L);
        address.setRegion("Region");
        address.setStreet("Street");
        insuranceObject.setAddress(address);
        contract.setInsuranceObject(insuranceObject);

        calculateService.calculate(contract);

        assertTrue(contract.getInsuranceObject().getInsurancePremium() > 0d);
    }

    private java.util.Date getDate(int year, int month, int dayOfMonth) {
        return Date.from(LocalDate.of(year, month, dayOfMonth).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
