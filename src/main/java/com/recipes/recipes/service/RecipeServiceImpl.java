package com.recipes.recipes.service;
import com.recipes.recipes.event.RecipeCreatedEvent;
import com.recipes.recipes.model.Recipe;
import com.recipes.recipes.model.Tag;
import com.recipes.recipes.repository.RecipeRepository;
import com.recipes.recipes.repository.UserRepository;
import com.recipes.recipes.repository.TagRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {
    
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public List<Recipe> getPublicOrOwnRecipes(int userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User with id " + userId + " does not exist.");
        }
        return recipeRepository.findPublicOrOwnRecipes(userId);
    }

    @Override
    public List<Recipe> getRecipesByUserId(int id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with id " + id + " does not exist.");
        }
        return recipeRepository.findByUserId(id);
    }

    @Override
    public List<Recipe> findPublicOrOwnRecipesByTags(List<String> tags, int userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User with id " + userId + " does not exist.");
        }

        if(tags == null || tags.isEmpty()) {
            throw new IllegalArgumentException("Invalid tag name.");        
        }

        List<Tag> validTags = tagRepository.findTagsByNameIn(tags);
        if (validTags.isEmpty()) {
            throw new IllegalArgumentException("No matching tags found.");
        }

        List<Recipe> recipes = recipeRepository.findPublicOrOwnRecipesByTags(tags, userId);
        return recipes;
    }

    @Override
    public Recipe getRecipeById(int id) {
        return recipeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
    }

    @Override
    @Transactional
    public Recipe addRecipe(Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("Recipe cannot be null.");
        }
    
        if (recipe.getUserId() == 0 || !userRepository.existsById(recipe.getUserId())) {
            throw new RuntimeException("Valid user must be associated with the recipe.");
        }

        // If in recipe are placed tags, which don't exist in the tags table, than we create them
        // Real use of @Transactional - two db calls (save new tag to tags table + save new recipe to recipe table)
        List<Tag> tags = new ArrayList<Tag>();
        if (recipe.getTags() != null) {
            for (Tag tag: recipe.getTags()) {
                Optional<Tag> existingTag = tagRepository.findByName(tag.getName());
                if (existingTag.isPresent()) {
                    tags.add(existingTag.get());
                } else {
                    Tag newTag = new Tag();
                    newTag.setName(tag.getName());
                    Tag savedTag = tagRepository.save(newTag);
                    tags.add(savedTag);
                }
            }
        }
        
        recipe.setTags(tags);
        Recipe savedRecipe = recipeRepository.save(recipe);

        // We publish the event here, that we have saved the new recipe, 
        // so the emailNotificationService will subscribe and send an email to the user with the new recipe
        eventPublisher.publishEvent(new RecipeCreatedEvent(this, savedRecipe));
        return savedRecipe;
    }

    @Override
    @Transactional
    public Recipe editRecipe(Recipe recipe) {
        if (recipe == null || recipe.getId() == 0) {
            throw new IllegalArgumentException("Recipe must have a valid ID to be edited.");
        }
    
        Optional<Recipe> existing = recipeRepository.findById(recipe.getId());
        if (!existing.isPresent()) {
            new RuntimeException("Recipe with id " + recipe.getId() + " does not exist.");
        }
        return recipeRepository.save(recipe);
    }

    @Override
    @Transactional
    public void removeTagFromRecipe(int recipeId, String tagName) {
        Recipe recipe = recipeRepository.findById(recipeId)
            .orElseThrow(() -> new RuntimeException("Recipe with id " + recipeId + " does not exist."));

        Tag tag = tagRepository.findByName(tagName)
            .orElseThrow(() -> new RuntimeException("Tag with name " + tagName + " does not exist."));

        if (!recipe.getTags().contains(tag)) {
            throw new RuntimeException("Tag " + tagName + " is not associated with recipe id: " + recipeId);
        }

        recipe.getTags().remove(tag);
        recipeRepository.save(recipe);
    }

    @Override
    @Transactional
    public void deleteRecipe(int id) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Recipe with passed id, not found"));
        recipeRepository.delete(recipe);
    }
}