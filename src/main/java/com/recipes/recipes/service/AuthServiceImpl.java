package com.recipes.recipes.service;
import com.recipes.recipes.dto.UserLogin;
import com.recipes.recipes.dto.UserRegister;
import com.recipes.recipes.model.User;
import com.recipes.recipes.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User register(UserRegister registerRequest) {
        User currUser = userRepository.findByUsername(registerRequest.getUsername());
        if (currUser != null) {
            throw new RuntimeException("Username already taken");
        }

        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(registerRequest.getPassword());
        return userRepository.save(newUser);
    }

    @Override
    public User login(UserLogin loginRequest) {
        User currUser = userRepository.findByUsername(loginRequest.getUsername());
        if (currUser == null) {
            throw new RuntimeException("User doesn't exist");
        }

        if (!currUser.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return currUser;
    }
}
