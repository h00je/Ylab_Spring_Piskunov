package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.storage.Storage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserStorage implements Storage<Long, User> {
    private final Map<Long, User> userMap;
    private Long userId;

    public UserStorage() {
        this.userMap = new HashMap<>();
        this.userId = 0L;
    }

    @Override
    public User save(User user) {
        user.setId(generatedUserId());
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User getById(Long userId) {
        return userMap.get(userId);
    }

    @Override
    public User update(User user) {
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteById(Long userId) {
        userMap.remove(userId);
    }

    public synchronized Long generatedUserId() {
        return ++userId;
    }
}
