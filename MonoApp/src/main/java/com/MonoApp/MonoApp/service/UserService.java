package com.MonoApp.MonoApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MonoApp.MonoApp.model.User;
import com.MonoApp.MonoApp.repository.UserRepository;

import java.sql.Date;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public User createUser(User user) {
        user.setRegisterDate(new Date(System.currentTimeMillis()));
        return userRepo.save(user);
    }

    public User getUser(UUID id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
