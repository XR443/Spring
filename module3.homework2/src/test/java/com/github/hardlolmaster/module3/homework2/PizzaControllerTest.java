package com.github.hardlolmaster.module3.homework2;

import com.github.hardlolmaster.module3.homework2.controller.RestPizzaController;
import com.github.hardlolmaster.module3.homework2.controller.RestPizzaTypeController;
import com.github.hardlolmaster.module3.homework2.dao.PizzaRepository;
import com.github.hardlolmaster.module3.homework2.dao.PizzaTypeRepository;
import com.github.hardlolmaster.module3.homework2.domain.Pizza;
import com.github.hardlolmaster.module3.homework2.domain.PizzaAddition;
import com.github.hardlolmaster.module3.homework2.domain.PizzaType;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class PizzaControllerTest {
    @Autowired
    private RestPizzaController controller;
    @Autowired
    private PizzaRepository repository;

    @Test
    public void testRest() {
        assertNotNull(controller);
        assertNotNull(repository);

        Pizza pizza = new Pizza();
        PizzaType pizzaType = new PizzaType();
        pizzaType.setName("Square");
        pizza.setPizzaType(pizzaType);
        PizzaAddition pizzaAddition = new PizzaAddition();
        pizzaAddition.setName("Cheese");
        pizza.getPizzaAdditions().add(pizzaAddition);
        ResponseEntity<Boolean> createResponse = controller.create(pizza);
        assertNotNull(createResponse);
        assertNotNull(createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertTrue(createResponse.getBody());

        List<Pizza> pizzaTypes = controller.pizza();
        assertNotNull(pizzaTypes);
        List<Pizza> all = repository.findAll();
        assertNotNull(all);
        assertEquals(all, pizzaTypes);

        pizzaType.setName("Round");
        pizzaAddition.setName("Monterey Jack");
        ResponseEntity<Boolean> editResponse = controller.edit(pizza);
        assertNotNull(editResponse);
        assertNotNull(editResponse.getStatusCode());
        assertNotNull(editResponse.getBody());
        assertEquals(HttpStatus.OK, editResponse.getStatusCode());
        assertTrue(editResponse.getBody());
        Optional<Pizza> optional = repository.findById(pizza.getId());
        assertTrue(optional.isPresent());
        assertEquals(optional.get(), pizza);

        ResponseEntity<Boolean> deleteResponse = controller.delete(pizza);
        assertNotNull(deleteResponse);
        assertNotNull(deleteResponse.getStatusCode());
        assertNotNull(deleteResponse.getBody());
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertTrue(deleteResponse.getBody());

        List<Pizza> all1 = repository.findAll();
        assertNotNull(all1);
        assertTrue(all1.isEmpty());
    }

}
