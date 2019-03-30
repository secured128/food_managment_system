

package com.elalex.food.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;

@Entity
public class SelectRecipeQueryDB implements Serializable
{
    static public final  int NUMBER_OF_PARAMS=8;
    @JsonIgnore
    @Transient
    private ObjectMapper jsonMapper = new ObjectMapper();



/*
    @JsonProperty("recipeName")
    @Id
    @Column(name = "recipe_name")
    private String recipeName;

    @JsonProperty("instructionDescription")
    @Column(name = "instruction_description")
    private String instructionDescription;

    @JsonProperty("code1")
    @Column(name = "code1")
    private Integer code1;

    @JsonProperty("quantity")
    @Column(name = "quantity")
    private Double quantity;

    @JsonProperty("sizeOfRecipe")
    @Column(name = "size_of_recipe")
    private Double sizeOfRecipe;

    @JsonProperty("quantityForRecipe")
    @Column(name = "quantity_for_recipe")
    private Double quantityForRecipe;

    @JsonProperty("unit")
    @Column(name = "unit")
    private String unit;
*/



    @JsonProperty("recipeDescriptionName")
    @Id
    @Column(name = "description")
    private String recipeDescriptionName;


    @JsonProperty("calculatedQuantity")
    @Column(name = "calculated_quantity")
    private Double calculatedQuantity;

    @JsonProperty("unit")
    @Column(name = "unit")
    private String unit;

    public SelectRecipeQueryDB()
    {

    }

    public SelectRecipeQueryDB(String json) throws Exception
    {
        SelectRecipeQueryDB selectRecipeQueryDB = jsonMapper.readValue(json, SelectRecipeQueryDB.class);
        this.setRecipeDescriptionName(selectRecipeQueryDB.getRecipeDescriptionName());
      //  this.setLinkedRecipeDescName(selectRecipeQueryDB.getLinkedRecipeDescName());
        this.setCalculatedQuantity(selectRecipeQueryDB.getCalculatedQuantity());
        this.setUnit(selectRecipeQueryDB.getUnit());
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




    public String getRecipeDescriptionName() {
        return recipeDescriptionName;
    }

    public void setRecipeDescriptionName(String recipeDescriptionName) {
        this.recipeDescriptionName = recipeDescriptionName;
    }


    public Double getCalculatedQuantity() {
        return calculatedQuantity;
    }

    public void setCalculatedQuantity(Double calculatedQuantity) {
        this.calculatedQuantity = calculatedQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}