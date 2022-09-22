package com.edu.ulab.app.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class User {
    private Long id;
    private String fullName;
    private String title;
    private Long age;
    private List<Book> books;
}
