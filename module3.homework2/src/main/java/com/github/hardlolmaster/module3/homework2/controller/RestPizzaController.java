package com.github.hardlolmaster.module3.homework2.controller;

import com.github.hardlolmaster.module3.homework2.dao.PizzaAdditionRepository;
import com.github.hardlolmaster.module3.homework2.dao.PizzaRepository;
import com.github.hardlolmaster.module3.homework2.dao.PizzaTypeRepository;
import com.github.hardlolmaster.module3.homework2.domain.Pizza;
import com.github.hardlolmaster.module3.homework2.domain.PizzaAddition;
import com.github.hardlolmaster.module3.homework2.domain.PizzaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class RestPizzaController {
    private final PizzaRepository pizzaRepository;
    private final PizzaTypeRepository pizzaTypeRepository;
    private final PizzaAdditionRepository pizzaAdditionRepository;

    @Autowired
    public RestPizzaController(PizzaRepository pizzaRepository, PizzaTypeRepository pizzaTypeRepository, PizzaAdditionRepository pizzaAdditionRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaTypeRepository = pizzaTypeRepository;
        this.pizzaAdditionRepository = pizzaAdditionRepository;
    }


    @GetMapping("/pizza")
    public List<Pizza> pizza() {
        return pizzaRepository.findAll();
    }

    @PostMapping("/pizza/create")
    public ResponseEntity<Boolean> create(@RequestBody Pizza pizza) {
        Pizza entity = reattachPizza(pizza);
        pizzaRepository.save(entity);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/pizza/delete")
    public ResponseEntity<Boolean> delete(@RequestBody Pizza pizza) {
        pizza = reattachPizza(pizza);
        pizzaRepository.delete(pizza);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/pizza/edit")
    public ResponseEntity<Boolean> edit(@RequestBody Pizza pizza) {
        pizza = reattachPizza(pizza);
        pizzaRepository.save(pizza);
        return ResponseEntity.ok(true);
    }

    private Pizza reattachPizza(Pizza from) {
        Optional<PizzaType> optionalPizzaType = pizzaTypeRepository.findById(from.getPizzaType().getId());
        if (optionalPizzaType.isEmpty()) {
            throw new RuntimeException(String.format("Pizza Type: %s Not Found", from.getPizzaType().getName()));
        }
        List<PizzaAddition> additions = from.getPizzaAdditions().stream().map(addition -> {
            Optional<PizzaAddition> optionalPizzaAddition = pizzaAdditionRepository.findById(addition.getId());
            if (optionalPizzaAddition.isEmpty()) {
                throw new RuntimeException(String.format("Pizza Addition: %s Not Found", addition.getName()));
            }
            return optionalPizzaAddition.get();
        }).collect(Collectors.toList());
        Pizza to = new Pizza();
        from.setPizzaType(optionalPizzaType.get());
        from.setPizzaAdditions(additions);
        return from;
    }
}
