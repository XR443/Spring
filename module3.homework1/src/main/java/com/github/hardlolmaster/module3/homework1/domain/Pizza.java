package com.github.hardlolmaster.module3.homework1.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Pizza {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PizzaType pizzaType;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PizzaAddition> pizzaAdditions;
}
