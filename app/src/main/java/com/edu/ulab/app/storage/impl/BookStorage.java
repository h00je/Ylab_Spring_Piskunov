package com.edu.ulab.app.storage.impl;


import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.storage.Storage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BookStorage implements Storage<Long, Book> {
    private final Map<Long, Book> bookMap;

    private Long bookId;

    public BookStorage() {
        this.bookMap = new HashMap<>();
        this.bookId = 0L;
    }

    @Override
    public Book save(Book book) {
        book.setId(generatedUserId());
        bookMap.put(book.getId(), book);
        return book;
    }

    @Override
    public Book getById(Long bookId) {
        return bookMap.get(bookId);
    }

    @Override
    public Book update(Book book) {
        bookMap.put(book.getId(), book);
        return book;
    }

    @Override
    public void deleteById(Long bookId) {
        bookMap.remove(bookId);
    }

    public synchronized Long generatedUserId() {
        return ++bookId;
    }
}
