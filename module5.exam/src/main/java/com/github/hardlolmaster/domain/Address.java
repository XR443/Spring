package com.github.hardlolmaster.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Address extends IdObject {
    private String country;
    private String index;
    private String district;
    private String region;
    private String city;
    private Long house;
    private String housing;
    private String building;
    private Long apartment;
    private String street;
}
