package com.github.hardlolmaster.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class Post {
    @Id
    private String id;
    private User user;
    private String content;
    private List<Comment> comments;
}
