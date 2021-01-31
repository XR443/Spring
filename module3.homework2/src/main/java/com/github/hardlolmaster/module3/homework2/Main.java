package com.github.hardlolmaster.module3.homework2;

import com.github.hardlolmaster.module3.homework2.dao.PizzaAdditionRepository;
import com.github.hardlolmaster.module3.homework2.dao.PizzaTypeRepository;
import com.github.hardlolmaster.module3.homework2.domain.PizzaAddition;
import com.github.hardlolmaster.module3.homework2.domain.PizzaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Main {

    public Main() {
    }

    @Autowired
    public Main(PizzaTypeRepository pizzaTypeRepository, PizzaAdditionRepository pizzaAdditionRepository) {
        this.pizzaTypeRepository = pizzaTypeRepository;
        this.pizzaAdditionRepository = pizzaAdditionRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    private PizzaTypeRepository pizzaTypeRepository;
    private PizzaAdditionRepository pizzaAdditionRepository;

    @PostConstruct
    public void init() {
        PizzaType type = new PizzaType();
        type.setName("Round");
        pizzaTypeRepository.save(type);

        PizzaAddition addition = new PizzaAddition();
        addition.setName("Monterey Jack");
        pizzaAdditionRepository.save(addition);
    }
}
