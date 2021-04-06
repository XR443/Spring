package com.github.hardlolmaster;

import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import com.github.hardlolmaster.repository.ContractRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ContractRepositoryTest {
    @Autowired
    private ContractRepository repository;

    @Test
    public void testExisting() {
        PropertyInsuranceContract entity = new PropertyInsuranceContract();
        entity.setNumber("123456");
        PropertyInsuranceContract save = repository.save(entity);
        assertEquals(entity, save);

        if (!repository.existsByNumber(entity.getNumber())) {
            fail("Не найден договор по номеру");
        }

    }
}
