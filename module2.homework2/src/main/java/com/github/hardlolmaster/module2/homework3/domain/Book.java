package com.github.hardlolmaster.module2.homework3.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Genre.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Author.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Comment.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private List<Comment> comments = new ArrayList<>();

}
