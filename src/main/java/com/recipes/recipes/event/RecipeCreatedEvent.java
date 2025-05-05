package com.recipes.recipes.event;

import com.recipes.recipes.model.Recipe;
import org.springframework.context.ApplicationEvent;

public class RecipeCreatedEvent extends ApplicationEvent {

    // The final keyword means that the recipe can't be reassigned after the event
    private final Recipe recipe;

    public RecipeCreatedEvent(Object source, Recipe recipe) {
        // Source - is the object that published the event
        // Calls the constructor of ApplicationEvent
        super(source);
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
