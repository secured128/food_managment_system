package com.elalex.food.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeDescriptionDBRepository extends CrudRepository<RecipeDescriptionDB, Long> {

    @Query(value=
          "select * from recipe_description where recipe_name = ?1", nativeQuery = true)
    RecipeDescriptionDB findByRecipeName(String recipeName);
}
