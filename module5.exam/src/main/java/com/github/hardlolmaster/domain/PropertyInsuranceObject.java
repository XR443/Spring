package com.github.hardlolmaster.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class PropertyInsuranceObject extends IdObject {
    private Double insuranceSum;
    private Double insurancePremium;
    private Long constructionYear;
    private Double area;
    private String propertyType;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;
}
