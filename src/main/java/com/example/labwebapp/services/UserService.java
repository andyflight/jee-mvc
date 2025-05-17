package com.example.labwebapp.services;

import com.example.labwebapp.models.User;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> getUserById(UUID id);
    Optional<User> getUserByName(String name);
    User createUser(String name);
}
