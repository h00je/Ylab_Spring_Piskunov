package com.edu.ulab.app.entity;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Book {
    private Long id;
    private Long userId;
    private String title;
    private String author;
    private Long pageCount;
}
