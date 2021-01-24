package com.github.hardlolmaster.module3.homework1.controllers;

import com.github.hardlolmaster.module3.homework1.dao.PizzaTypeRepository;
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
public class PizzaTypeController {
    private final PizzaTypeRepository typeRepository;

    @Autowired
    public PizzaTypeController(PizzaTypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @GetMapping("/pizzaType")
    public String pizzaType(Model model) {
        List<PizzaType> all = typeRepository.findAll();
        model.addAttribute("pizzaTypes", all);
        return "pizzaType";
    }

    @PostMapping("/pizzaType/create")
    public String createPizzaType(@RequestParam String type, Model model) {
        PizzaType entity = new PizzaType();
        entity.setName(type);
        typeRepository.save(entity);
        return pizzaType(model);
    }

    @PostMapping("/pizzaType/delete")
    public String deletePizzaType(@RequestParam Long id, Model model) {
        typeRepository.deleteById(id);
        return pizzaType(model);
    }

    @PostMapping("/pizzaType/update")
    public String updatePizzaType(@RequestParam Long id, @RequestParam String newType, Model model) {
        Optional<PizzaType> optional = typeRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException(String.format("Pizza Type with id %d not found", id));
        }
        optional.get().setName(newType);
        typeRepository.save(optional.get());
        return pizzaType(model);
    }
}
