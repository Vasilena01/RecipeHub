package com.recipes.recipes.service;

import com.recipes.recipes.event.RecipeCreatedEvent;

public interface EmailNotificationService {
    void handleRecipeCreatedEvent(RecipeCreatedEvent event);
}
