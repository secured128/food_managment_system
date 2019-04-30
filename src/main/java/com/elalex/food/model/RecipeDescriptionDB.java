
package com.elalex.food.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;


//@JsonPropertyOrder({"id"})
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "recipe_description")
public class RecipeDescriptionDB implements Serializable
{
    static public final  int NUMBER_OF_PARAMS=4;//we put 4 and npt 5, since image is not uploaded from excel
    static public final  int IMAGE_COLUMN=4;
    @JsonIgnore
    @Transient
    private ObjectMapper jsonMapper = new ObjectMapper();


    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonProperty("recipeName")
    @Column(name = "recipe_name")
    private String recipeName;

    @JsonProperty("description")
    @Column(name = "description")
    private String description;

    @JsonProperty("image")
    @Column(name = "image")
    private byte[] image;

    @JsonProperty("size_of_recipe")
    @Column(name = "size_of_recipe")
    private Double sizeOfRecipe;

    @JsonProperty("unit")
    @Column(name = "unit")
    private String unit;

    public RecipeDescriptionDB()
    {
    }

    public RecipeDescriptionDB(  String dbStructure[], HashMap<String, byte[]> picturesMap)
    {
        this.setRecipeName(dbStructure[0]);
        this.setDescription(dbStructure[1]);
//        this.setImage(Long.parseLong(dbStructure[2]));
        this.setSizeOfRecipe(Double.parseDouble(dbStructure[2]));
        this.setUnit(dbStructure[3]);
        if (picturesMap != null)
         this.image = picturesMap.get(dbStructure[0]);

    }

    public RecipeDescriptionDB(String json) throws Exception
    {
        RecipeDescriptionDB recipeDescriptionDB = jsonMapper.readValue(json, RecipeDescriptionDB.class);
        this.setRecipeName(recipeDescriptionDB.getRecipeName());
        this.setDescription(recipeDescriptionDB.getDescription());
        this.setSizeOfRecipe(recipeDescriptionDB.getSizeOfRecipe());
        this.setUnit(recipeDescriptionDB.getUnit());
        this.setImage(recipeDescriptionDB.getImage());
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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Double getSizeOfRecipe() {
        return sizeOfRecipe;
    }

    public void setSizeOfRecipe(Double sizeOfRecipe) {
        this.sizeOfRecipe = sizeOfRecipe;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}