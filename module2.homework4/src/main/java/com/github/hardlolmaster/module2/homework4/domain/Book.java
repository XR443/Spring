package com.github.hardlolmaster.module2.homework4.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Book implements Serializable {
    @Id
    private BigInteger id;
    private String name;
    private Genre genre;
    private Author author;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Transient
    private List<Comment> comments = new ArrayList<>();

}
