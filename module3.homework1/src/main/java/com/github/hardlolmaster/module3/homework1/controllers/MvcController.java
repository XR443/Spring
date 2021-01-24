package com.github.hardlolmaster.module3.homework1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {
    @GetMapping("/welcome")
    public String welcome(Model model) {
        return "welcome";
    }
}
