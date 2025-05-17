package com.example.labwebapp.repositories;

import com.example.labwebapp.models.User;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepositoryStubImpl implements UserRepository {

    private static final UserRepository instance = new UserRepositoryStubImpl();

    private final Map<UUID, User> users = new ConcurrentHashMap<>();

    private UserRepositoryStubImpl() {
        // Default constructor
    }

    public static UserRepository getInstance() {
        return instance;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByName(String name) {
        return users.values().stream()
                .filter(user -> user.getName().equals(name))
                .findFirst();
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        }
        users.put(user.getId(), user);
        return user;
    }

}
