package com.recipes.recipes.controller;
import com.recipes.recipes.model.Tag;
import com.recipes.recipes.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tags")
public class TagController {
@Autowired
    private TagService tagService;

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/{tagName}")
    public Optional<Tag> findByName(@PathVariable String tagName) {        
        return tagService.findByName(tagName); 
    }

    @PostMapping("/add")
    public Tag addTag(@Valid @RequestBody Tag tag) { 
        return tagService.addTag(tag);
    }

    @DeleteMapping("/delete/{name}")
    public void deleteTagByName(@PathVariable String name) { 
        tagService.deleteTagByName(name);
    }
}
