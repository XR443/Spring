package com.github.hardlolmaster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hardlolmaster.actions.*;
import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.Individual;
import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import com.github.hardlolmaster.domain.PropertyInsuranceObject;
import com.github.hardlolmaster.repository.ContractRepository;
import com.github.hardlolmaster.repository.IndividualRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CommonActionTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IndividualRepository individualRepository;
    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CalculatePropertyInsurancePremiumAction calculatePropertyInsurancePremiumAction;

    @Test
    public void testCalculateInsurancePremiumAction() throws JsonProcessingException {
        PropertyInsuranceContract contract = new PropertyInsuranceContract();
        contract.setInsurancePeriodFrom(getDate(2021, 1, 1));
        contract.setInsurancePeriodTo(getDate(2022, 2, 2));
        PropertyInsuranceObject insuranceObject = new PropertyInsuranceObject();
        insuranceObject.setInsuranceSum(100_000d);
        insuranceObject.setPropertyType("Квартира");
        insuranceObject.setArea(50d);
        insuranceObject.setConstructionYear(2015L);
        contract.setInsuranceObject(insuranceObject);

        Map<?, ?> input = getAsMap(contract);
        ResponseObject<?> responseObject = calculatePropertyInsurancePremiumAction.execute(input);
        assertNotNull(responseObject);
        assertTrue(responseObject.isOk());
        assertNotNull(responseObject.getPayload());
        assertTrue(((PropertyInsuranceContract) responseObject.getPayload()).getInsuranceObject().getInsurancePremium() > 0d);
    }

    @Autowired
    private FindIndividualsByNameAction findIndividualsByNameAction;

    @Test
    public void testFindIndividualsByNameAction() throws JsonProcessingException {
        {
            {
                Individual entity = new Individual();
                entity.setLastName("LastName");
                entity.setFirstName("FirstName");
                entity.setSecondName("SecoNdName");
                individualRepository.save(entity);
            }
            {
                Individual entity = new Individual();
                entity.setLastName("lastname");
                entity.setFirstName("firstname");
                entity.setSecondName("secondName");
                individualRepository.save(entity);
            }
        }
        {
            Individual object = new Individual();
            object.setLastName("Lastname");
            object.setFirstName("fIrStName");
            ResponseObject<?> responseObject = findIndividualsByNameAction.execute(getAsMap(object));
            assertNotNull(responseObject);
            assertTrue(responseObject.isOk());
            assertNotNull(responseObject.getPayload());
            assertEquals(2, ((List<?>) responseObject.getPayload()).size());
        }
        {
            Individual object = new Individual();
            object.setLastName("Lastname");
            object.setFirstName("fIrStName");
            object.setSecondName("seCONdNaME");
            ResponseObject<?> responseObject = findIndividualsByNameAction.execute(getAsMap(object));
            assertNotNull(responseObject);
            assertTrue(responseObject.isOk());
            assertNotNull(responseObject.getPayload());
            assertEquals(2, ((List<?>) responseObject.getPayload()).size());
        }
    }

    @Autowired
    private GetAllContractsAction getAllContractsAction;

    @Test
    public void testGetAllContractsAction() {
        contractRepository.save(new PropertyInsuranceContract());
        contractRepository.save(new PropertyInsuranceContract());
        contractRepository.save(new PropertyInsuranceContract());

        ResponseObject<?> execute = getAllContractsAction.execute(null);

        assertNotNull(execute);
        assertTrue(execute.isOk());
        assertNotNull(execute.getPayload());
        assertEquals(3, ((List<?>) execute.getPayload()).size());
    }

    @Autowired
    private SaveContractAction saveContractAction;

    @Test
    public void testSaveContractAction() {
        ResponseObject<?> execute = saveContractAction.execute(new PropertyInsuranceContract());

        assertNotNull(execute);
        assertTrue(execute.isOk());
        assertNotNull(execute.getPayload());

        assertEquals(1, contractRepository.findAll().size());

    }

    @Autowired
    private SaveIndividualAction saveIndividualAction;

    @Test
    public void testSaveIndividualAction() {
        ResponseObject<?> execute = saveIndividualAction.execute(new Individual());

        assertNotNull(execute);
        assertTrue(execute.isOk());
        assertNotNull(execute.getPayload());

        assertEquals(1, individualRepository.findAll().size());

    }

    private java.util.Date getDate(int year, int month, int dayOfMonth) {
        return Date.from(LocalDate.of(year, month, dayOfMonth).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    private Map<?, ?> getAsMap(Object object) throws JsonProcessingException {
        return objectMapper.readValue(objectMapper.writeValueAsString(object), Map.class);
    }
}
