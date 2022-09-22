package com.edu.ulab.app.customRepository;

import com.edu.ulab.app.entity.Book;

import java.util.Optional;

public interface BookRepository {
    Book createBook(Book book);

    Book updateBook(Book book);

    Optional<Book> getBookById(Long bookId);

    void deleteBookById(Long bookId);
}
