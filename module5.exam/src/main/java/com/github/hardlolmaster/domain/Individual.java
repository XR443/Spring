package com.github.hardlolmaster.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Individual extends IdObject {
    private String lastName;
    private String firstName;
    private String secondName;
    private Date birthdate;
    private String citizenship;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Document document;
}
