package com.github.hardlolmaster;

import com.github.hardlolmaster.domain.Individual;
import com.github.hardlolmaster.repository.IndividualRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class IndividualRepositoryTest {
    @Autowired
    private IndividualRepository repository;

    @Test
    public void repositoryTest() {
        Individual individual = new Individual();
        individual.setLastName("LastName");
        individual.setFirstName("FirstName");
        individual.setSecondName("SecondName");

        Individual save = repository.save(individual);
        assertNotNull(save.getId());
        assertEquals(individual, save);

        Optional<Individual> byId = repository.findById(individual.getId());
        assertTrue(byId.isPresent());
        assertEquals(individual, byId.get());

        List<Individual> byFirstNameAndLastName =
                repository.findByFirstNameIsLikeIgnoreCaseAndLastNameIsLikeIgnoreCase("%firsTnAme%", "%laStnAme%");
        assertFalse(byFirstNameAndLastName.isEmpty());
        assertEquals(1, byFirstNameAndLastName.size());
        assertEquals(individual, byFirstNameAndLastName.get(0));

        List<Individual> byFirstNameIsLikeAndLastNameIsLikeAndSecondNameIsLike = repository
                .findByFirstNameIsLikeIgnoreCaseAndLastNameIsLikeIgnoreCaseAndSecondNameIsLikeIgnoreCase(
                        "%FirstName%", "%LastName%", "%SeCondNaMe%");
        assertFalse(byFirstNameIsLikeAndLastNameIsLikeAndSecondNameIsLike.isEmpty());
        assertEquals(1, byFirstNameIsLikeAndLastNameIsLikeAndSecondNameIsLike.size());
        assertEquals(individual, byFirstNameIsLikeAndLastNameIsLikeAndSecondNameIsLike.get(0));
    }
}
