package com.recipes.recipes.service;
import com.recipes.recipes.model.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserByUsername(String username);
    User addUser(User user);
    User editUser(User user);
    void deleteUser(int id);
}
