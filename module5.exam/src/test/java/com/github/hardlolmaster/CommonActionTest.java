package com.github.hardlolmaster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hardlolmaster.actions.*;
import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.*;
import com.github.hardlolmaster.repository.ContractRepository;
import com.github.hardlolmaster.repository.IndividualRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CommonActionTest
{
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IndividualRepository individualRepository;
    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CalculatePropertyInsurancePremiumAction calculatePropertyInsurancePremiumAction;

    @Test
    public void testCalculateInsurancePremiumAction() throws JsonProcessingException
    {
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
        insurer.setBirthday(getDate(2000, 1, 1));
        Document document = new Document();
        document.setNumber("111111");
        document.setSeries("1111");
        document.setIssuedWhen(getDate(2020, 1, 1));
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
    public void testFindIndividualsByNameAction() throws JsonProcessingException
    {
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
    @Autowired
    private MutableAclService aclService;

    @Test
    @WithMockUser()
    @Transactional
    public void testGetAllContractsAction()
    {
        savePropertyInsuranceContract();
        savePropertyInsuranceContract();
        savePropertyInsuranceContract();

        ResponseObject<?> execute = getAllContractsAction.execute(null);

        assertNotNull(execute);
        assertTrue(execute.isOk());
        assertNotNull(execute.getPayload());
        assertEquals(3, ((List<?>) execute.getPayload()).size());
    }

    private PropertyInsuranceContract savePropertyInsuranceContract()
    {
        PropertyInsuranceContract save = contractRepository.save(new PropertyInsuranceContract());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Sid owner = new PrincipalSid(auth);
        Sid role_user = new GrantedAuthoritySid("ROLE_USER");
        ObjectIdentity oid = new ObjectIdentityImpl(PropertyInsuranceContract.class,
                save.getId());
        MutableAcl acl = aclService.createAcl(oid);
        acl.setOwner(owner);
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, role_user, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, role_user, true);
        aclService.updateAcl(acl);
        return save;
    }

    @Autowired
    private SaveContractAction saveContractAction;

    @Test
    @WithMockUser
    @Transactional
    public void testSaveContractAction()
    {
//        PropertyInsuranceContract contract = savePropertyInsuranceContract();
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
        insurer.setBirthday(getDate(2000, 1, 1));
        Document document = new Document();
        document.setNumber("111111");
        document.setSeries("1111");
        document.setIssuedWhen(getDate(2020, 1, 1));
        document.setIssuedBy("1111111");
        insurer.setDocument(document);
        contract.setInsurer(individualRepository.save(insurer));
        PropertyInsuranceObject insuranceObject = new PropertyInsuranceObject();
        insuranceObject.setInsuranceSum(100_000d);
        insuranceObject.setPropertyType("Квартира");
        insuranceObject.setArea(50d);
        insuranceObject.setConstructionYear(2015L);
        insuranceObject.setInsurancePremium(100d);
        Address address = new Address();
        address.setApartment(11L);
        address.setCity("city");
        address.setCountry("Country");
        address.setHouse(10L);
        address.setRegion("Region");
        address.setStreet("Street");
        insuranceObject.setAddress(address);
        contract.setInsuranceObject(insuranceObject);
//        contractRepository.save(contract);
                ResponseObject<?> execute = saveContractAction.execute(contract);

        //        assertNotNull(execute);
        //        assertTrue(execute.isOk());
        //        assertNotNull(execute.getPayload());

        assertEquals(1, contractRepository.findAll().size());

    }

    @Autowired
    private SaveIndividualAction saveIndividualAction;

    @Test
    public void testSaveIndividualAction()
    {
        Individual individual = new Individual();
        individual.setFirstName("FirstName");
        individual.setLastName("LastName");
        individual.setBirthday(getDate(2000, 1, 18));
        individual.setDocument(new Document());
        individual.getDocument().setSeries("1234");
        individual.getDocument().setNumber("123456");
        ResponseObject<?> execute = saveIndividualAction.execute(individual);

        assertNotNull(execute);
        assertTrue(execute.isOk());
        assertNotNull(execute.getPayload());

        assertEquals(1, individualRepository.findAll().size());

    }

    private java.util.Date getDate(int year, int month, int dayOfMonth)
    {
        return Date.from(LocalDate.of(year, month, dayOfMonth).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    private Map<?, ?> getAsMap(Object object) throws JsonProcessingException
    {
        return objectMapper.readValue(objectMapper.writeValueAsString(object), Map.class);
    }
}
