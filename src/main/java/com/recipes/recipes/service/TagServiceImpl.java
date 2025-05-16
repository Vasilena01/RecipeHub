package com.recipes.recipes.service;
import com.recipes.recipes.model.Tag;
import com.recipes.recipes.model.Recipe;
import com.recipes.recipes.repository.TagRepository;
import com.recipes.recipes.repository.RecipeRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;

    public TagServiceImpl(TagRepository tagRepository, RecipeRepository recipeRepository, RecipeService recipeService) {
        this.recipeRepository = recipeRepository;
        this.recipeService = recipeService;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Tag name cannot be null or empty.");
        }
        return tagRepository.findByName(name);
    }

    @Override
    @Transactional
    public Tag addTag(Tag tag) {
        if (tag == null || tag.getName().trim() == null || tag.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tag name cannot be null or empty.");
        }

        Optional<Tag> existingTag = tagRepository.findByName(tag.getName().trim());
        if (existingTag.isPresent()) {
            // If tag already exists, return existing tag, without adding the new one
            return existingTag.get();
        }
        return tagRepository.save(tag);
    }

    @Override
    @Transactional
    public void deleteTagByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Tag name cannot be null or empty.");
        }
        Optional<Tag> tag = tagRepository.findByName(name);
        if (tag.isPresent()) {
            List<Recipe> recipes = recipeRepository.findRecipesByTagName(name);

            //  Delete all tag occurences in recipes, if there are recipes containing it
            if (recipes != null && ! recipes.isEmpty()) {
                for (Recipe recipe: recipes) {
                    recipeService.removeTagFromRecipe(recipe.getId(), name);
                }
            }
            tagRepository.delete(tag.get());
        } else {
            throw new IllegalArgumentException("Tag doesn't exist.");
        }
    }
}
