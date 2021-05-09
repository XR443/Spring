package com.github.hardlolmaster.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Individual
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String lastName;
    private String firstName;
    private String secondName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String citizenship;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Document document;
}
