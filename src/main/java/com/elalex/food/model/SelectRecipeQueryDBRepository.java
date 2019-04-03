

package com.elalex.food.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by alexb on 2/20/2018.
 */
@Repository
public interface SelectRecipeQueryDBRepository extends CrudRepository<SelectRecipeQueryDB, Long> {



  /* @Query( "SELECT recipe_description.recipeName,\n" +
           "       instructions_description.instructionDescription,\n" +
           "       1,\n" +
           "       1,\n" +
           "       recipe_description.sizeOfRecipe,\n" +
           "       1,\n" +
           "       1\n" +
           "FROM RecipeDescriptionDB recipe_description,\n" +
           "     InstructionsDescriptionDB instructions_description,\n" +
           "     RecipeInstructionsOrderDB recipe_instructions_order\n" +
           "WHERE recipe_description.recipeName =recipe_instructions_order.recipeDescriptionName\n" +
           "  AND recipe_instructions_order.instructionName = instructions_description.instructName\n" +
           "  AND recipe_description.recipeName = ?1\n" +
           "ORDER BY recipe_instructions_order.instructionOrder")
    List <SelectRecipeQueryDB> selectRecipeOrder( String recipeName);

    @Query( "SELECT recipe_description.recipeName,\n" +
            "       instructions_description.instructionDescription,\n" +
            "       product.code1,\n" +
            "       recipe_products.quantity,\n" +
            "       recipe_description.sizeOfRecipe,\n" +
            "       ((recipe_products.quantity * ?2)/recipe_description.sizeOfRecipe) as quantity_for_recipe,\n" +
            "       product.unit\n" +
            "FROM RecipeDescriptionDB recipe_description,\n" +
            "     InstructionsDescriptionDB instructions_description,\n" +
            "     RecipeInstructionsOrderDB recipe_instructions_order,\n" +
            "     RecipeProductsDB recipe_products,\n" +
            "     ProductDB product\n" +
            "WHERE recipe_description.recipeName =recipe_instructions_order.recipeDescriptionName\n" +
            "  AND recipe_instructions_order.instructionName = instructions_description.instructName\n" +
            "  AND recipe_description.recipeName = recipe_products.recipeDescName\n" +
            "  AND recipe_products.productId = product.id\n" +
            "  AND recipe_description.recipeName = ?1\n" +
            "ORDER BY recipe_instructions_order.instructionOrder")
    List <SelectRecipeQueryDB> selectRecipeProduct( String recipeName, Double quantity);*/


    @Query (value=
            "WITH RECURSIVE " +
                    "input_recipe_factor AS" +
                    "(SELECT ?2/size_of_recipe as orig_factor FROM recipe_description WHERE recipe_name = ?1),"+
                    "recipe_join AS\n" +
                    "  (SELECT *\n" +
                    "   FROM recipe_instructions_order RI,\n" +
                    "        recipe_description RD\n" +
                    "   WHERE RD.recipe_name = coalesce(RI.linked_recipe_desc_name, RI.recipe_description_name)),\n" +
                    "               cte AS\n" +
                    "  (SELECT R1.recipe_description_name,\n" +
                    "          R1.linked_recipe_desc_name,\n" +
                    "          instruction_name,\n" +
                    "          quantity_of_linked_recipe parent_actual_size,\n" +
                    "          R1.size_of_recipe original_linked_size,\n" +
                    "          quantity_of_linked_recipe/R1.size_of_recipe AS factor,\n" +
                    "          R1.size_of_recipe size_of_recipe,\n" +
                    "          instruction_order,\n" +
                    "          0 AS LEVEL\n" +
                    "   FROM recipe_join R1\n" +
                    "   WHERE (R1.recipe_description_name =?1)\n" +
                    "     UNION  ALL\n" +
                    "     SELECT child.recipe_description_name,\n" +
                    "            child.linked_recipe_desc_name,\n" +
                    "            child.instruction_name,\n" +
                    "            child.quantity_of_linked_recipe,\n" +
                    "            quantity_of_linked_recipe,\n" +
                    "            factor * (quantity_of_linked_recipe/ child.size_of_recipe) AS factor,\n" +
                    "            child.size_of_recipe,\n" +
                    "            child.instruction_order,\n" +
                    "            LEVEL +1 AS LEVEL\n" +
                    "     FROM recipe_join AS child\n" +
                    "     JOIN cte AS parent ON child.recipe_description_name = parent.linked_recipe_desc_name)\n" +
                    "SELECT  product_id,\n" +
                    "        description,\n" +
                    "       sum(calculated_quantity) *orig_factor calculated_quantity,\n" +
                    "       unit\n" +
                    "FROM input_recipe_factor,\n" +
                    "  (SELECT  product_id,\n" +
                    "           description,\n" +
                    "          factor*quantity calculated_quantity,\n" +
                    "          unit\n" +
                    "   FROM cte,\n" +
                    "        recipe_products,\n" +
                    "        product\n" +
                    "   WHERE recipe_products.recipe_desc_name = linked_recipe_desc_name\n" +
                    "     AND recipe_products.product_id = product.id\n" +
                    "   UNION ALL SELECT product_id,\n" +
                    "                    description,\n" +
                    "                    quantity calculated_quantity,\n" +
                    "                    unit\n" +
                    "   FROM  recipe_products,\n" +
                    "        product\n" +
                    "   WHERE recipe_products.recipe_desc_name = ?1\n" +
                    "     AND recipe_products.product_id = product.id ) prd\n" +
                    "GROUP BY  product_id,\n" +
                    "          description,\n" +
                    "         unit, orig_factor\n" +
                    "ORDER BY description", nativeQuery = true)


   /* @Query (value=
            "WITH RECURSIVE recipe_join AS\n" +
                    "  (SELECT *\n" +
                    "   FROM recipe_instructions_order RI,\n" +
                    "        recipe_description RD\n" +
                    "   WHERE RD.recipe_name = coalesce(RI.linked_recipe_desc_name, RI.recipe_description_name)),\n" +
                    "               cte AS\n" +
                    "  (SELECT R1.recipe_description_name,\n" +
                    "          R1.linked_recipe_desc_name,\n" +
                    "          instruction_name,\n" +
                    "          quantity_of_linked_recipe parent_actual_size,\n" +
                    "          R1.size_of_recipe original_linked_size,\n" +
                    "          quantity_of_linked_recipe/R1.size_of_recipe AS factor,\n" +
                    "          R1.size_of_recipe size_of_recipe,\n" +
                    "          instruction_order,\n" +
                    "          0 AS LEVEL\n" +
                    "   FROM recipe_join R1\n" +
                    "   WHERE (R1.recipe_description_name =?1)\n" +
                    "     UNION  ALL\n" +
                    "     SELECT child.recipe_description_name,\n" +
                    "            child.linked_recipe_desc_name,\n" +
                    "            child.instruction_name,\n" +
                    "            child.quantity_of_linked_recipe,\n" +
                    "            quantity_of_linked_recipe,\n" +
                    "            factor * (quantity_of_linked_recipe/ child.size_of_recipe) AS factor,\n" +
                    "            child.size_of_recipe,\n" +
                    "            child.instruction_order,\n" +
                    "            LEVEL +1 AS LEVEL\n" +
                    "     FROM recipe_join AS child\n" +
                    "     JOIN cte AS parent ON child.recipe_description_name = parent.linked_recipe_desc_name)\n" +
                    "SELECT description,\n" +
                    "       sum(calculated_quantity) *?2 calculated_quantity,\n" +
                    "       unit\n" +
                    "FROM\n" +
                    "  (SELECT description,\n" +
                    "          factor*quantity calculated_quantity,\n" +
                    "          unit\n" +
                    "   FROM cte,\n" +
                    "        recipe_products,\n" +
                    "        product\n" +
                    "   WHERE recipe_products.recipe_desc_name = linked_recipe_desc_name\n" +
                    "     AND recipe_products.product_id = product.id\n" +
                    "   UNION ALL SELECT description,\n" +
                    "                    quantity calculated_quantity,\n" +
                    "                    unit\n" +
                    "   FROM recipe_products,\n" +
                    "        product\n" +
                    "   WHERE recipe_products.recipe_desc_name = ?1\n" +
                    "     AND recipe_products.product_id = product.id ) prd\n" +
                    "GROUP BY description,\n" +
                    "         unit\n" +
                    "ORDER BY description", nativeQuery = true)*/
    List <SelectRecipeQueryDB> selectRecipeProductLinked( String recipeName, Double requiredSize);


}
