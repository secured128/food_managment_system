package com.elalex.food.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SelectRecipeInstructionsDBRepository extends CrudRepository<SelectRecipeInstructionsDB, Long> {

    @Query(value=
            "WITH RECURSIVE cte AS\n" +
                    "  (SELECT R1.recipe_description_name,\n" +
                    "          R1.linked_recipe_desc_name,\n" +
                    "          instruction_name,\n" +
                    "          0 AS LEVEL,\n" +
                    "          instruction_order\n" +
                    "   FROM recipe_instructions_order R1\n" +
                    "   WHERE (R1.recipe_description_name =?1)\n" +
                    "     UNION  ALL\n" +
                    "     SELECT child.recipe_description_name,\n" +
                    "            child.linked_recipe_desc_name,\n" +
                    "            child.instruction_name,\n" +
                    "            LEVEL +1 AS LEVEL,\n" +
                    "                   child.instruction_order\n" +
                    "     FROM recipe_instructions_order AS child\n" +
                    "     JOIN cte AS parent ON child.recipe_description_name = parent.linked_recipe_desc_name)\n" +
                    "SELECT cte.*,recipe_description_name||level||instruction_order as id_field,\n" +
                    "       instruction_description\n" +
                    "FROM cte ,\n" +
                    "     instructions_description\n" +
                    "WHERE cte.instruction_name = instructions_description.instruct_name\n" +
                    "ORDER BY LEVEL ASC, instruction_order ASC,\n" +
                    "                    recipe_description_name ASC,\n" +
                    "                    linked_recipe_desc_name DESC", nativeQuery = true)
    List<SelectRecipeInstructionsDB> selectRecipeInstructions(String recipeName);
}
