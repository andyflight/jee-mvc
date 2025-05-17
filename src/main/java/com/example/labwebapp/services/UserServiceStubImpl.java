package com.example.labwebapp.services;

import com.example.labwebapp.models.User;
import com.example.labwebapp.repositories.UserRepository;
import com.example.labwebapp.repositories.UserRepositoryStubImpl;

import java.util.Optional;
import java.util.UUID;

public class UserServiceStubImpl implements  UserService{

    UserRepository repository = UserRepositoryStubImpl.getInstance();

    public UserServiceStubImpl() {
        // Default constructor
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Optional<User> getUserByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public User createUser(String name) {
        User user = new User();
        user.setName(name);
        return repository.save(user);
    }

}
