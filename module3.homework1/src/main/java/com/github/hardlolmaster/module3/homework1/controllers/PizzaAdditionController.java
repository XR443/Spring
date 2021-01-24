package com.github.hardlolmaster.module3.homework1.controllers;

import com.github.hardlolmaster.module3.homework1.dao.PizzaAdditionRepository;
import com.github.hardlolmaster.module3.homework1.domain.PizzaAddition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class PizzaAdditionController {
    private final PizzaAdditionRepository additionRepository;

    @Autowired
    public PizzaAdditionController(PizzaAdditionRepository additionRepository) {
        this.additionRepository = additionRepository;
    }

    @GetMapping("/pizzaAddition")
    public String pizzaAddition(Model model) {
        List<PizzaAddition> all = additionRepository.findAll();
        model.addAttribute("pizzaAdditions", all);
        return "pizzaAddition";
    }

    @PostMapping("/pizzaAddition/create")
    public String createPizzaAddition(@RequestParam String type, Model model) {
        PizzaAddition entity = new PizzaAddition();
        entity.setName(type);
        additionRepository.save(entity);
        return pizzaAddition(model);
    }

    @PostMapping("/pizzaAddition/delete")
    public String deletePizzaAddition(@RequestParam Long id, Model model) {
        additionRepository.deleteById(id);
        return pizzaAddition(model);
    }

    @PostMapping("/pizzaAddition/update")
    public String updatePizzaAddition(@RequestParam Long id, @RequestParam String newType, Model model) {
        Optional<PizzaAddition> optional = additionRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException(String.format("Pizza Addition with id %d not found", id));
        }
        optional.get().setName(newType);
        additionRepository.save(optional.get());
        return pizzaAddition(model);
    }
}
