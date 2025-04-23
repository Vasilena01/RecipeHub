package com.recipes.recipes.controller;

import com.recipes.recipes.model.Tag;
import com.recipes.recipes.dto.ApiResponse;
import com.recipes.recipes.service.TagService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tags")
public class TagController {
@Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Tag>>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        ApiResponse<List<Tag>> response = new ApiResponse<List<Tag>>(
            "All tags fetched successfully", tags, 200);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{tagName}")
    public ResponseEntity<ApiResponse<Optional<Tag>>> findByName(@PathVariable String tagName) {        
        Optional<Tag> tag = tagService.findByName(tagName);
        ApiResponse<Optional<Tag>> response = new ApiResponse<Optional<Tag>>(
            "Tag with the given name fetched successfully", tag, 200);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Tag>> addTag(@Valid @RequestBody Tag tag) { 
        Tag newTag = tagService.addTag(tag);
        ApiResponse<Tag> response = new ApiResponse<Tag>(
            "Tag added successfully", newTag, 201);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<ApiResponse<String>> deleteTagByName(@PathVariable String name) { 
        tagService.deleteTagByName(name);
        ApiResponse<String> response = new ApiResponse<String>(
            "Tag deleted successfully and was also removed from all recipes containing it", 
            null, 200);
        return ResponseEntity.ok(response);
    }
}
