package com.recipes.recipes.service;
import com.recipes.recipes.model.User;
import com.recipes.recipes.dto.UserLogin;
import com.recipes.recipes.dto.UserRegister;

public interface AuthService {
    User register(UserRegister registerRequest);
    User login(UserLogin loginRequest);
}
