package com.recipes.recipes.controller;
import com.recipes.recipes.model.Recipe;
import com.recipes.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/visible/{userId}")
    public List<Recipe> getPublicOrOwnRecipes(@PathVariable int userId) {
        return recipeService.getPublicOrOwnRecipes(userId);
    }

    @GetMapping("/user/{userId}")
    public List<Recipe> getRecipesByUser(@PathVariable int userId) {
        return recipeService.getRecipesByUserId(userId);
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable int id) {
        return recipeService.getRecipeById(id);
    }

    @GetMapping("/tag/{tagName}")
    public List<Recipe> findRecipesByTagName(@PathVariable String tagName) {
        return recipeService.findRecipesByTagName(tagName);
    }

    @PostMapping("/add")
    public Recipe addRecipe(@Valid @RequestBody Recipe recipe) {
        return recipeService.addRecipe(recipe);
    }

    @PutMapping("/edit")
    public Recipe editRecipe(@Valid @RequestBody Recipe recipe) {
        return recipeService.editRecipe(recipe);
    }

    @DeleteMapping("/{recipeId}/delete/{tagName}")
    public void removeTagFromRecipe(@PathVariable int recipeId, @PathVariable String tagName) {
        recipeService.removeTagFromRecipe(recipeId, tagName);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRecipe(@PathVariable int id) {
        recipeService.deleteRecipe(id);
    }
}