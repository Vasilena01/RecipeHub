package com.recipes.recipes.controller;
import com.recipes.recipes.model.Recipe;
import com.recipes.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/user/{userId}/recipes")
    public List<Recipe> getRecipesByUser(@PathVariable int userId) {
        return recipeService.getRecipesByUserId(userId);
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable int id) {
        return recipeService.getRecipeById(id);
    }

    @PostMapping
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        return recipeService.addRecipe(recipe);
    }

    @PutMapping
    public Recipe editRecipe(@RequestBody Recipe recipe) {
        return recipeService.editRecipe(recipe);
    }

    @DeleteMapping("/id")
    public void deleteRecipe(@PathVariable int id) {
        recipeService.deleteRecipe(id);
    }
}
