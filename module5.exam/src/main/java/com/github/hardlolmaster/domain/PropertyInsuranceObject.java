package com.github.hardlolmaster.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PropertyInsuranceObject
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Double insuranceSum;
    private Double insurancePremium;
    private Long constructionYear;
    private Double area;
    private String propertyType;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;
}
