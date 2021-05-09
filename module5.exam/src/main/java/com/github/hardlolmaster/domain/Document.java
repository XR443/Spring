package com.github.hardlolmaster.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Document
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String series;
    private String number;
    private String issuedBy;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date issuedWhen;
}
