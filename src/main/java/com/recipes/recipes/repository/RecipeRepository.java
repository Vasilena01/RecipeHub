package com.recipes.recipes.repository;
import com.recipes.recipes.model.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findByUserId(int userId);

    @Query("SELECT DISTINCT r FROM Recipe r JOIN r.tags t WHERE t.name IN :tags")
    List<Recipe> findRecipesByTags(@Param("tags") List<String> tags);

    @Query("SELECT r FROM Recipe r WHERE r.isPrivate = false OR r.userId = :userId")
    List<Recipe> findPublicOrOwnRecipes(@Param("userId") int userId);
}
