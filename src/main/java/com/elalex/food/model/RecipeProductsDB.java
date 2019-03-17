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
public class RecipeProductsDB implements Serializable
{
    static public final  int NUMBER_OF_PARAMS=4;
    @JsonIgnore
    @Transient
    private ObjectMapper jsonMapper = new ObjectMapper();

    @JsonProperty("id")
    @Id
    private Long id;

    @JsonProperty("recipeDescName")
    @Column(name = "recipe_desc_name")
    private String recipeDescName;

    @JsonProperty("productId")
    @Column(name = "product_id")
    private Long productId;

    @JsonProperty("quantity")
    @Column(name = "quantity")
    private Double quantity;



    public RecipeProductsDB(  String dbStructure[])
    {
        this.setId(Long.parseLong(dbStructure[0]));
        this.setRecipeDescName(dbStructure[1]);
        this.setProductId(Long.parseLong(dbStructure[2]));
        this.setQuantity(Double.parseDouble(dbStructure[3]));
    }

    public RecipeProductsDB(String json) throws Exception
    {
        RecipeProductsDB recipeProductsDB = jsonMapper.readValue(json, RecipeProductsDB.class);
        this.setId(recipeProductsDB.getId());
        this.setRecipeDescName(recipeProductsDB.getRecipeDescName());
        this.setProductId(recipeProductsDB.getProductId());
        this.setQuantity(recipeProductsDB.getQuantity());

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

    public String getRecipeDescName() {
        return recipeDescName;
    }

    public void setRecipeDescName(String recipeDescName) {
        this.recipeDescName = recipeDescName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}