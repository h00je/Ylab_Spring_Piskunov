package com.edu.ulab.app.customRepository;

import com.edu.ulab.app.entity.User;

import java.util.Optional;

public interface UserRepository {
    User createUser(User user);

    User updateUser(User user);

    Optional<User> getUserById(Long userId);

    void deleteUserById(Long userId);
}
