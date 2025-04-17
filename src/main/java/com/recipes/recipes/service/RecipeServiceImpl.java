package com.recipes.recipes.service;
import com.recipes.recipes.model.Recipe;
import com.recipes.recipes.repository.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    
    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public List<Recipe> getRecipesByUserId(int id) {
        return recipeRepository.findByUserId(id);
    }

    @Override
    public Recipe getRecipeById(int id) {
        return recipeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe editRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(int id) {
        recipeRepository.deleteById(id);
    }
}
