package com.example.labwebapp.services;

import com.example.labwebapp.models.User;
import com.example.labwebapp.repositories.UserRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.Optional;
import java.util.UUID;

@Stateless
public class UserServiceStubImpl implements  UserService{

    @EJB
    private UserRepository repository;

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
