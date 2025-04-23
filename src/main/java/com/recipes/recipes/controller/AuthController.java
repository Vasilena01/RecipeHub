package com.recipes.recipes.controller;

import com.recipes.recipes.model.User;
import com.recipes.recipes.dto.UserLogin;
import com.recipes.recipes.dto.ApiResponse;
import com.recipes.recipes.dto.UserRegister;
import com.recipes.recipes.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@Valid @RequestBody UserRegister registerRequest) {
        User newUser = authService.register(registerRequest);
        ApiResponse<User> response = new ApiResponse<User>("User registered successfully", newUser, 201);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> login(@Valid @RequestBody UserLogin loginRequest) {
        User loggedInUser = authService.login(loginRequest);
        ApiResponse<User> response = new ApiResponse<User>("User loged in successfully", loggedInUser, 200);
        return ResponseEntity.ok(response);
    }
}