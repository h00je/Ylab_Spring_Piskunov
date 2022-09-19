package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.customRepository.CustomBookRepository;
import com.edu.ulab.app.customRepository.CustomUserRepository;
import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    private final CustomBookRepository bookRepository;
    private final CustomUserRepository userRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(CustomBookRepository bookRepository, CustomUserRepository userRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = bookRepository.createBook(bookMapper.bootDtoToBook(bookDto));
        Optional<User> user = userRepository.getUserById(bookDto.getUserId());
        if (user.isPresent()) {
            user.get().getBooks().add(book);
            userRepository.updateUser(user.get());
            return bookMapper.bookToBookDto(book);
        }
        throw new NotFoundException("User not found");
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        if (bookRepository.getBookById(bookDto.getId()).isPresent()) {
            return bookMapper.bookToBookDto(bookRepository.updateBook(bookMapper.bootDtoToBook(bookDto)));
        }
        log.info("Got request update non-existent book {}", bookDto);
        throw new NotFoundException("Book for update not found");
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookRepository.getBookById(id)
                .map(bookMapper::bookToBookDto)
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteBookById(id);
    }

    @Override
    public List<BookDto> getBookByUserId(Long userId) {
        return userRepository.getUserById(userId).map(user -> user.getBooks().stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookToBookDto)
                .collect(Collectors.toList())).orElse(Collections.emptyList());
    }
}
