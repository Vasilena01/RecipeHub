package com.recipes.recipes.service;
import com.recipes.recipes.model.User;
import com.recipes.recipes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }

        User user = userRepository.findByUsername(username.trim());
        if (user == null) {
            throw new RuntimeException("User not found with username: " + username);
        }

        return user;
    }

    @Override
    public User addUser(User user) {
        if (user == null || user.getUsername().trim() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("User or username cannot be null or empty.");
        }

        if (userRepository.findByUsername(user.getUsername().trim()) != null) {
            throw new RuntimeException("Username already exists: " + user.getUsername());
        }

        user.setUsername(user.getUsername().trim());
        return userRepository.save(user);
    }

    @Override
    public User editUser(User user) {
        if (user == null || user.getId() == 0) {
            throw new IllegalArgumentException("User or user ID must be valid.");
        }

        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + user.getId());
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + id);
        }

        userRepository.deleteById(id);
    }
}
