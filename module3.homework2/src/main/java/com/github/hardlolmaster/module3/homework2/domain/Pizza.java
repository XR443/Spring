package com.github.hardlolmaster.module3.homework2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Pizza {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private PizzaType pizzaType;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PizzaAddition> pizzaAdditions = new ArrayList<>();
}
