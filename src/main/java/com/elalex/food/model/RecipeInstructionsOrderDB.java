

package com.elalex.food.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;




//@JsonPropertyOrder({"id"})
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "recipe_instructions_order")
public class RecipeInstructionsOrderDB implements Serializable
{
    static public final  int NUMBER_OF_PARAMS=6;
    @JsonIgnore
    @Transient
    private ObjectMapper jsonMapper = new ObjectMapper();

    @JsonProperty("id")
    @Id
    private Long id;

    @JsonProperty("recipeDescriptionName")
    @Column(name = "recipe_description_name")
    private String recipeDescriptionName;

    @JsonProperty("instructionName")
    @Column(name = "instruction_name")
    private String instructionName;

    @JsonProperty("linkedRecipeDescName")
    @Column(name = "linked_recipe_desc_name")
    private String linkedRecipeDescName;

    @JsonProperty("quantityOfLinkedRecipe")
    @Column(name = "quantity_of_linked_recipe")
    private Double quantityOfLinkedRecipe;

    @JsonProperty("instructionOrder")
    @Column(name = "instruction_order")
    private Integer instructionOrder;

    public RecipeInstructionsOrderDB(  String dbStructure[])
    {
        this.setId(Long.parseLong(dbStructure[0]));
        this.setRecipeDescriptionName(dbStructure[1]);
        this.setInstructionName(dbStructure[2]);
        this.setLinkedRecipeDescName(dbStructure[3]);
        this.setQuantityOfLinkedRecipe(Double.parseDouble(dbStructure[4]));
        this.setInstructionOrder(Integer.getInteger(dbStructure[5]));
    }

    public RecipeInstructionsOrderDB(String json) throws Exception
    {
        RecipeInstructionsOrderDB recipeInstructionsOrderDB = jsonMapper.readValue(json, RecipeInstructionsOrderDB.class);
        this.setId(recipeInstructionsOrderDB.getId());
        this.setRecipeDescriptionName(recipeInstructionsOrderDB.getRecipeDescriptionName());
        this.setInstructionName(recipeInstructionsOrderDB.getInstructionName());
        this.setLinkedRecipeDescName(recipeInstructionsOrderDB.getLinkedRecipeDescName());
        this.setQuantityOfLinkedRecipe(recipeInstructionsOrderDB.getQuantityOfLinkedRecipe());
        this.setInstructionOrder(recipeInstructionsOrderDB.getInstructionOrder());
    }


    @Override
    public String toString() {
        StringWriter sw = new StringWriter();
        try {
            new ObjectMapper().writeValue(sw, this);
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipeDescriptionName() {
        return recipeDescriptionName;
    }

    public void setRecipeDescriptionName(String recipeDescriptionName) {
        this.recipeDescriptionName = recipeDescriptionName;
    }

    public String getInstructionName() {
        return instructionName;
    }

    public void setInstructionName(String instructionName) {
        this.instructionName = instructionName;
    }

    public String getLinkedRecipeDescName() {
        return linkedRecipeDescName;
    }

    public void setLinkedRecipeDescName(String linkedRecipeDescName) {
        this.linkedRecipeDescName = linkedRecipeDescName;
    }

    public Double getQuantityOfLinkedRecipe() {
        return quantityOfLinkedRecipe;
    }

    public void setQuantityOfLinkedRecipe(Double quantityOfLinkedRecipe) {
        this.quantityOfLinkedRecipe = quantityOfLinkedRecipe;
    }

    public Integer getInstructionOrder() {
        return instructionOrder;
    }

    public void setInstructionOrder(Integer instructionOrder) {
        this.instructionOrder = instructionOrder;
    }
}