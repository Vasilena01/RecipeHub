package com.recipes.recipes.controller;

import com.recipes.recipes.dto.UserLogin;
import com.recipes.recipes.dto.UserRegister;
import com.recipes.recipes.model.User;
import com.recipes.recipes.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User register(@Valid @RequestBody UserRegister registerRequest) {
        User newUser = authService.register(registerRequest);
        return newUser;
    }

    @PostMapping("/login")
    public User login(@Valid @RequestBody UserLogin loginRequest) {
        User loggedInUser = authService.login(loginRequest);
        return loggedInUser;
    }
}