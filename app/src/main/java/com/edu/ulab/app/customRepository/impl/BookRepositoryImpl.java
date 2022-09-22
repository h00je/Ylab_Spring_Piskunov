package com.edu.ulab.app.customRepository.impl;

import com.edu.ulab.app.customRepository.BookRepository;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.storage.Storage;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookRepositoryImpl implements BookRepository {
    private final Storage<Long, Book> bookStorage;

    public BookRepositoryImpl(Storage<Long, Book> bookStorage) {
        this.bookStorage = bookStorage;
    }

    @Override
    public Book createBook(Book book) {
        return bookStorage.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        return bookStorage.update(book);
    }

    @Override
    public Optional<Book> getBookById(Long bookId) {
        return Optional.ofNullable(bookStorage.getById(bookId));
    }

    @Override
    public void deleteBookById(Long bookId) {
        bookStorage.deleteById(bookId);
    }
}
