package com.github.hardlolmaster.module3.homework1.controllers;

import com.github.hardlolmaster.module3.homework1.dao.PizzaAdditionRepository;
import com.github.hardlolmaster.module3.homework1.dao.PizzaRepository;
import com.github.hardlolmaster.module3.homework1.dao.PizzaTypeRepository;
import com.github.hardlolmaster.module3.homework1.domain.Pizza;
import com.github.hardlolmaster.module3.homework1.domain.PizzaAddition;
import com.github.hardlolmaster.module3.homework1.domain.PizzaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class PizzaController {
    private final PizzaRepository pizzaRepository;
    private final PizzaTypeRepository typeRepository;
    private final PizzaAdditionRepository additionRepository;

    @Autowired
    public PizzaController(PizzaRepository pizzaRepository, PizzaTypeRepository typeRepository, PizzaAdditionRepository additionRepository) {
        this.pizzaRepository = pizzaRepository;
        this.typeRepository = typeRepository;
        this.additionRepository = additionRepository;
    }

    @GetMapping("/pizza")
    public String pizza(Model model) {
        Boolean pizzaCreated = (Boolean) model.getAttribute("pizzaCreated");
        if (pizzaCreated == null) {
            model.addAttribute("pizzaCreated", false);
        }
        List<PizzaAddition> additions = additionRepository.findAll();
        model.addAttribute("pizzaAdditions", additions);
        List<PizzaType> pizzaTypes = typeRepository.findAll();
        model.addAttribute("pizzaTypes", pizzaTypes);
        model.addAttribute("allPizza", pizzaRepository.findAll());
        return "pizza";
    }

    @PostMapping("/pizza/create")
    public String createPizza(Model model) {
        model.addAttribute("pizzaCreated", true);
        return pizza(model);
    }

    @PostMapping("/pizza/bake")
    public String bakePizza(@RequestParam Long pizzaTypeId, @RequestParam List<Long> pizzaAdditionsId, Model model) {
        Optional<PizzaType> pizzaType = typeRepository.findById(pizzaTypeId);
        if (pizzaType.isEmpty()) {
            throw new RuntimeException(String.format("PizzaType with id %d not found", pizzaTypeId));
        }
        List<PizzaAddition> additions = additionRepository.findAllById(pizzaAdditionsId);
        if (additions.isEmpty()) {
            throw new RuntimeException(String.format("Pizza Addition with id %d not found", pizzaAdditionsId.get(0)));
        }
        Pizza pizza = Pizza.builder()
                .pizzaType(pizzaType.get())
                .pizzaAdditions(additions)
                .build();
        pizzaRepository.save(pizza);
        return pizza(model);
    }
}
