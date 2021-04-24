package com.github.hardlolmaster.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    private String citizenship;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Document document;
}
