package com.github.hardlolmaster.module3.homework2;

import com.github.hardlolmaster.module3.homework2.controller.RestPizzaAdditionController;
import com.github.hardlolmaster.module3.homework2.dao.PizzaAdditionRepository;
import com.github.hardlolmaster.module3.homework2.domain.PizzaAddition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class PizzaAdditionControllerTest {

    @Autowired
    private RestPizzaAdditionController controller;
    @Autowired
    private PizzaAdditionRepository repository;

    @Test
    public void testRest() {
        assertNotNull(controller);
        assertNotNull(repository);

        PizzaAddition addition = new PizzaAddition();
        addition.setName("Cheese");
        ResponseEntity<Boolean> createResponse = controller.create(addition);
        assertNotNull(createResponse);
        assertNotNull(createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertTrue(createResponse.getBody());

        List<PizzaAddition> pizzaAdditions = controller.pizzaAdditions();
        assertNotNull(pizzaAdditions);
        List<PizzaAddition> all = repository.findAll();
        assertNotNull(all);
        assertEquals(all, pizzaAdditions);

        addition.setName("Monterey Jack");
        ResponseEntity<Boolean> editResponse = controller.edit(addition);
        assertNotNull(editResponse);
        assertNotNull(editResponse.getStatusCode());
        assertNotNull(editResponse.getBody());
        assertEquals(HttpStatus.OK, editResponse.getStatusCode());
        assertTrue(editResponse.getBody());
        Optional<PizzaAddition> optional = repository.findById(addition.getId());
        assertTrue(optional.isPresent());
        assertEquals(optional.get(), addition);

        ResponseEntity<Boolean> deleteResponse = controller.delete(addition);
        assertNotNull(deleteResponse);
        assertNotNull(deleteResponse.getStatusCode());
        assertNotNull(deleteResponse.getBody());
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertTrue(deleteResponse.getBody());

        List<PizzaAddition> all1 = repository.findAll();
        assertNotNull(all1);
        assertTrue(all1.isEmpty());
    }

}
