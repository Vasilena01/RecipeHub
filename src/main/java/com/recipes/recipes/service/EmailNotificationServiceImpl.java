package com.recipes.recipes.service;
import com.recipes.recipes.model.Recipe;
import com.recipes.recipes.model.User;
import com.recipes.recipes.repository.UserRepository;
import com.recipes.recipes.event.RecipeCreatedEvent;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public EmailNotificationServiceImpl(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @EventListener
    public void handleRecipeCreatedEvent(RecipeCreatedEvent event) {
        Recipe recipe = event.getRecipe();
        Integer userId = recipe.getUserId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));
        
        String userEmail = user.getEmail();
        String recipeTitle = recipe.getTitle();
        String recipeDescription = recipe.getDescription();
        
        String subject = "New Recipe Created: " + recipe.getTitle();
        String text = "Hi, a new recipe titled '" + recipeTitle + "' has been added.\n\nDescription: " 
            + recipeDescription;

        emailService.sendEmail(userEmail, subject, text);
    }
}
