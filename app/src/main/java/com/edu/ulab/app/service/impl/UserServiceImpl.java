package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.customRepository.BookRepository;
import com.edu.ulab.app.customRepository.UserRepository;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, BookRepository bookRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userRepository.createUser(userMapper.userDtoToUser(userDto));
        log.info("Initialized user creation in the service layer : {}", user);
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto updateUserWithBook(UserDto userDto) {
        Optional<User> userById = userRepository.getUserById(userDto.getId());
        if (userById.isPresent()) {
            log.info("Initialized user update in the service layer : {}", userById);
            userById.get().getBooks().stream()
                    .filter(Objects::nonNull)
                    .map(Book::getId)
                    .forEach(bookRepository::deleteBookById);
            userById.get().getBooks().clear();
            return userMapper.userToUserDto(userRepository.updateUser(userById.get()));
        }
        log.info("Got request update non-existent user {}", userDto);
        throw new NotFoundException("User for update not found ");
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("Got request user in the service layer witch id: {}", id);
        return userRepository.getUserById(id)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Initializing delete user in the service layer witch id: {}", id);
        userRepository.getUserById(id).map(user -> user.getBooks().stream()
                        .filter(Objects::nonNull)
                        .map(Book::getId)
                        .peek(book -> log.info("Initializing delete book for user in the service layer: {}", book))
                        .collect(Collectors.toList())).orElse(Collections.emptyList())
                .forEach(bookRepository::deleteBookById);
        userRepository.deleteUserById(id);
    }
}
