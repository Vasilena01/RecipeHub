package com.recipes.recipes.service;
import com.recipes.recipes.model.Tag;
import java.util.List;
import java.util.Optional;

public interface TagService {
    List<Tag> getAllTags();
    Optional<Tag> findByName(String name);
    Tag addTag(Tag tag);
    void deleteTagByName(String name);
}
