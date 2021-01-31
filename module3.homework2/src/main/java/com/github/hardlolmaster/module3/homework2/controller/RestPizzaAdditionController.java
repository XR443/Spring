package com.github.hardlolmaster.module3.homework2.controller;

import com.github.hardlolmaster.module3.homework2.dao.PizzaAdditionRepository;
import com.github.hardlolmaster.module3.homework2.domain.PizzaAddition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestPizzaAdditionController {
    private final PizzaAdditionRepository repository;

    @Autowired
    public RestPizzaAdditionController(PizzaAdditionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/pizzaAddition")
    public List<PizzaAddition> pizzaAdditions() {
        return repository.findAll();
    }

    @PostMapping("/pizzaAddition/create")
    public ResponseEntity<Boolean> create(@RequestBody PizzaAddition addition) {
        repository.save(addition);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/pizzaAddition/delete")
    public ResponseEntity<Boolean> delete(@RequestBody PizzaAddition addition) {
        repository.delete(addition);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/pizzaAddition/edit")
    public ResponseEntity<Boolean> edit(@RequestBody PizzaAddition addition) {
        repository.save(addition);
        return ResponseEntity.ok(true);
    }
}
