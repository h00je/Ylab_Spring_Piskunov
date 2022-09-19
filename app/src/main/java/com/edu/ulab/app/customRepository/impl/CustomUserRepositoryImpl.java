package com.edu.ulab.app.customRepository.impl;

import com.edu.ulab.app.customRepository.CustomUserRepository;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.storage.Storage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class CustomUserRepositoryImpl implements CustomUserRepository {
    private final Storage<Long, User> userStorage;

    public CustomUserRepositoryImpl(Storage<Long, User> userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User createUser(User user) {
        user.setBooks(new ArrayList<>());
        return userStorage.save(user);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return Optional.ofNullable(userStorage.getById(userId));
    }

    @Override
    public User updateUser(User user) {
        return userStorage.update(user);
    }

    @Override
    public void deleteUserById(Long userId) {
        userStorage.deleteById(userId);
    }
}
