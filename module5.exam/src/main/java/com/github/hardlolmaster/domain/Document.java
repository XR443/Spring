package com.github.hardlolmaster.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Document extends IdObject {
    private String series;
    private String number;
    private String issuedBy;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date issuedWhen;
}
