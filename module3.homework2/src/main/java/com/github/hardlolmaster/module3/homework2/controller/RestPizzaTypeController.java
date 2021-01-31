package com.github.hardlolmaster.module3.homework2.controller;

import com.github.hardlolmaster.module3.homework2.dao.PizzaTypeRepository;
import com.github.hardlolmaster.module3.homework2.domain.PizzaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestPizzaTypeController {
    private final PizzaTypeRepository repository;

    @Autowired
    public RestPizzaTypeController(PizzaTypeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/pizzaType")
    public List<PizzaType> pizzaTypes() {
        return repository.findAll();
    }

    @PostMapping("/pizzaType/create")
    public ResponseEntity<Boolean> create(@RequestBody PizzaType type) {
        repository.save(type);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/pizzaType/delete")
    public ResponseEntity<Boolean> delete(@RequestBody PizzaType type) {
        repository.delete(type);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/pizzaType/edit")
    public ResponseEntity<Boolean> edit(@RequestBody PizzaType type) {
        repository.save(type);
        return ResponseEntity.ok(true);
    }
}
