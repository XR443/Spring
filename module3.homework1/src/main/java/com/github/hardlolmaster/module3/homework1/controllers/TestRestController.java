package com.github.hardlolmaster.module3.homework1.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {
    @RequestMapping(path = "/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("OK");
    }
}
