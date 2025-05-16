package com.recipes.recipes.controller;

import com.recipes.recipes.model.Recipe;
import com.recipes.recipes.dto.ApiResponse;
import com.recipes.recipes.service.RecipeService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Recipe>>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        ApiResponse<List<Recipe>> response = new ApiResponse<List<Recipe>>(
            "All recipes fetched successfully", recipes, 200);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/visible/{userId}")
    public ResponseEntity<ApiResponse<List<Recipe>>> getPublicOrOwnRecipes(@PathVariable int userId) {
        List<Recipe> recipes = recipeService.getPublicOrOwnRecipes(userId);
        ApiResponse<List<Recipe>> response = new ApiResponse<List<Recipe>>(
            "All public and current user's recipes fetched successfully", recipes, 200);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Recipe>>> getRecipesByUser(@PathVariable int userId) {
        List<Recipe> recipes = recipeService.getRecipesByUserId(userId);
        ApiResponse<List<Recipe>> response = new ApiResponse<List<Recipe>>(
            "All user's recipes fetched successfully", recipes, 200);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Recipe>> getRecipe(@PathVariable int id) {
        Recipe recipe = recipeService.getRecipeById(id);
        ApiResponse<Recipe> response = new ApiResponse<Recipe>(
            "Recipe fetched successfully", recipe, 200);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tags")
    public ResponseEntity<ApiResponse<List<Recipe>>> findRecipesByTags(
        @RequestParam List<String> tags, @RequestParam int userId) {
        List<Recipe> recipes = recipeService.findPublicOrOwnRecipesByTags(tags, userId);
        ApiResponse<List<Recipe>> response = new ApiResponse<List<Recipe>>(
            "All public and current user's recipes with given tags fetched successfully", recipes, 200);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Recipe>> addRecipe(@Valid @RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.addRecipe(recipe);
        ApiResponse<Recipe> response = new ApiResponse<Recipe>(
            "Recipe added successfully", newRecipe, 201);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/edit")
    public ResponseEntity<ApiResponse<Recipe>> editRecipe(@Valid @RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.editRecipe(recipe);
        ApiResponse<Recipe> response = new ApiResponse<Recipe>(
            "Recipe edited successfully", newRecipe, 200);
        return ResponseEntity.ok(response);    
    }

    @DeleteMapping("/{recipeId}/delete/{tagName}")
    public ResponseEntity<ApiResponse<String>> removeTagFromRecipe(@PathVariable int recipeId, @PathVariable String tagName) {
        recipeService.removeTagFromRecipe(recipeId, tagName);
        ApiResponse<String> response = new ApiResponse<String>(
            "Tag from given recipe removed successfully", null, 200);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteRecipe(@PathVariable int id) {
        recipeService.deleteRecipe(id);
        ApiResponse<String> response = new ApiResponse<String>(
            "Recipe deleted successfully", null, 200);
        return ResponseEntity.ok(response);
    }
}