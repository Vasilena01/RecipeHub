package com.recipes.recipes.repository;
import com.recipes.recipes.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByName(String name);
    List<Tag> findTagsByNameIn(List<String> names);
    boolean existsByName(String name);
}
