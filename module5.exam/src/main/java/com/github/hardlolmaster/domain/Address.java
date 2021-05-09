package com.github.hardlolmaster.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String country;
    @Column(name = "post_index")
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
