package com.recipes.recipes.service;
import com.recipes.recipes.model.Recipe;
import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();
    List<Recipe> getRecipesByUserId(int userId);
    List<Recipe> findRecipesByTagName(String tagName);
    Recipe getRecipeById(int id);
    Recipe addRecipe(Recipe recipe);
    Recipe editRecipe(Recipe recipe);
    void removeTagFromRecipe(int recipeId, String tagName);
    void deleteRecipe(int id);
}