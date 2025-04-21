package com.recipes.recipes.service;
import com.recipes.recipes.model.Tag;
import com.recipes.recipes.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

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
    public void deleteTagByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Tag name cannot be null or empty.");
        }
        Optional<Tag> tag = tagRepository.findByName(name);
        tag.ifPresent(tagRepository::delete);
    }
}
