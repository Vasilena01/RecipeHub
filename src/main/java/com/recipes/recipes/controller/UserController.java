package com.recipes.recipes.controller;

import com.recipes.recipes.model.User;
import com.recipes.recipes.service.UserService;
import com.recipes.recipes.dto.ApiResponse;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        ApiResponse<List<User>> response = new ApiResponse<List<User>>(
            "All users fetched successfully", users, 200);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<User>> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        ApiResponse<User> response = new ApiResponse<User>(
            "User fetched successfully", user, 200);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<User>> addUser(@Valid @RequestBody User user) {
        User newUser = userService.addUser(user);
        ApiResponse<User> response = new ApiResponse<User>(
            "User created successfully", newUser, 201);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/edit")
    public ResponseEntity<ApiResponse<User>> editUser(@Valid @RequestBody User user) {
        User editedUser = userService.editUser(user);
        ApiResponse<User> response = new ApiResponse<User>(
            "User edited successfully", editedUser, 200);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        ApiResponse<String> response = new ApiResponse<String>(
            "User deleted successfully", null, 200);
        return ResponseEntity.ok(response);
    }
}
