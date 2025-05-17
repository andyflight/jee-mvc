package com.example.labwebapp.repositories;

import com.example.labwebapp.models.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID id);
    Optional<User> findByName(String name);
    User save(User user);
}
