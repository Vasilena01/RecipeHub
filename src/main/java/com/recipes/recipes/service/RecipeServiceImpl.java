package com.recipes.recipes.service;
import com.recipes.recipes.model.Recipe;
import com.recipes.recipes.repository.RecipeRepository;
import com.recipes.recipes.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public List<Recipe> getRecipesByUserId(int id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with id " + id + " does not exist.");
        }
        return recipeRepository.findByUserId(id);
    }

    @Override
    public List<Recipe> findRecipesByTagName(String tagName) {
        System.out.println(tagName);
        if(tagName == null || tagName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid tag name.");        
        }

        List<Recipe> recipes = recipeRepository.findRecipesByTagName(tagName);

        if (recipes.isEmpty()) {
            throw new RuntimeException("No recipes found with tag: " + tagName);
        }
        return recipes;
    }

    @Override
    public Recipe getRecipeById(int id) {
        return recipeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("Recipe cannot be null.");
        }
    
        if (recipe.getUserId() == 0 || !userRepository.existsById(recipe.getUserId())) {
            throw new RuntimeException("Valid user must be associated with the recipe.");
        }
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe editRecipe(Recipe recipe) {
        if (recipe == null || recipe.getId() == 0) {
            throw new IllegalArgumentException("Recipe must have a valid ID to be edited.");
        }
    
        Recipe existing = recipeRepository.findById(recipe.getId())
            .orElseThrow(() -> new RuntimeException("Recipe with id " + recipe.getId() + " does not exist."));

        return recipeRepository.save(existing);
    }

    @Override
    public void deleteRecipe(int id) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Recipe with passed id, not found"));
        recipeRepository.delete(recipe);
    }
}