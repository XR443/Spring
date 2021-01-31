package com.github.hardlolmaster.module3.homework2;

import com.github.hardlolmaster.module3.homework2.controller.RestPizzaTypeController;
import com.github.hardlolmaster.module3.homework2.dao.PizzaTypeRepository;
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
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class PizzaTypeControllerTest {
    @Autowired
    private RestPizzaTypeController controller;
    @Autowired
    private PizzaTypeRepository repository;

    @Test
    public void testRest() {
        assertNotNull(controller);
        assertNotNull(repository);

        PizzaType pizzaType = new PizzaType();
        pizzaType.setName("Square");
        ResponseEntity<Boolean> createResponse = controller.create(pizzaType);
        assertNotNull(createResponse);
        assertNotNull(createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertTrue(createResponse.getBody());

        List<PizzaType> pizzaTypes = controller.pizzaTypes();
        assertNotNull(pizzaTypes);
        List<PizzaType> all = repository.findAll();
        assertNotNull(all);
        assertEquals(all, pizzaTypes);

        pizzaType.setName("Round");
        ResponseEntity<Boolean> editResponse = controller.edit(pizzaType);
        assertNotNull(editResponse);
        assertNotNull(editResponse.getStatusCode());
        assertNotNull(editResponse.getBody());
        assertEquals(HttpStatus.OK, editResponse.getStatusCode());
        assertTrue(editResponse.getBody());
        Optional<PizzaType> optional = repository.findById(pizzaType.getId());
        assertTrue(optional.isPresent());
        assertEquals(optional.get(), pizzaType);

        ResponseEntity<Boolean> deleteResponse = controller.delete(pizzaType);
        assertNotNull(deleteResponse);
        assertNotNull(deleteResponse.getStatusCode());
        assertNotNull(deleteResponse.getBody());
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertTrue(deleteResponse.getBody());

        List<PizzaType> all1 = repository.findAll();
        assertNotNull(all1);
        assertTrue(all1.isEmpty());
    }

}
