package com.github.hardlolmaster.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class PropertyInsuranceContract
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String number;
    private String comment;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date insurancePeriodFrom;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date insurancePeriodTo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date calculateDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date conclusionDate;
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    private Individual insurer;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PropertyInsuranceObject insuranceObject;
}
