package com.recipes.recipes.service;
import com.recipes.recipes.model.Recipe;
import java.util.List;

public interface RecipeService {
    List<Recipe> getRecipesByUserId(int userId);
    Recipe getRecipeById(int id);
    Recipe addRecipe(Recipe recipe);
    Recipe editRecipe(Recipe recipe);
    void deleteRecipe(int id);
}