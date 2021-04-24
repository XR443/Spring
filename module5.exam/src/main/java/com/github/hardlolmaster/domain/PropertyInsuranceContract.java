package com.github.hardlolmaster.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Proxy;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class PropertyInsuranceContract extends IdObject
{
    private String number;
    private String comment;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date insurancePeriodFrom;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date insurancePeriodTo;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date calculateDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date conclusionDate;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Individual insurer;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PropertyInsuranceObject insuranceObject;
}
