package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.customRepository.CustomBookRepository;
import com.edu.ulab.app.customRepository.CustomUserRepository;
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
    private final CustomUserRepository userRepository;
    private final CustomBookRepository bookRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(CustomUserRepository userRepository, CustomBookRepository bookRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userRepository.createUser(userMapper.userDtoToUser(userDto));
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto updateUserWithBook(UserDto userDto) {
        Optional<User> userById = userRepository.getUserById(userDto.getId());
        if (userById.isPresent()) {
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
        return userRepository.getUserById(id)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.getUserById(id).map(user -> user.getBooks().stream()
                        .filter(Objects::nonNull)
                        .map(Book::getId)
                        .collect(Collectors.toList())).orElse(Collections.emptyList())
                .forEach(bookRepository::deleteBookById);
        userRepository.deleteUserById(id);
    }
}
