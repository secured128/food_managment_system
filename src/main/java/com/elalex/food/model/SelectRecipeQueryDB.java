

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

    @JsonProperty("productId")
    @Id
    @Column(name = "product_id")
    private Long productId;


    @JsonProperty("productDescriptionName")
    @Column(name = "description")
    private String productDescriptionName;


    @JsonProperty("calculatedQuantity")
    @Column(name = "calculated_quantity")
    private Double calculatedQuantity;

    @JsonProperty("unit")
    @Column(name = "unit")
    private String unit;

    public SelectRecipeQueryDB()
    {

    }
    public SelectRecipeQueryDB(SelectRecipeQueryDB selectRecipeQueryDB)
    {
       this.productId = selectRecipeQueryDB.getProductId();
       this.productDescriptionName = selectRecipeQueryDB.getProductDescriptionName();
       this.calculatedQuantity = selectRecipeQueryDB.getCalculatedQuantity();
       this.unit = selectRecipeQueryDB.getUnit();
    }

    public SelectRecipeQueryDB(String json) throws Exception
    {
        SelectRecipeQueryDB selectRecipeQueryDB = jsonMapper.readValue(json, SelectRecipeQueryDB.class);
        this.setProductId(selectRecipeQueryDB.getProductId());
        this.setProductDescriptionName(selectRecipeQueryDB.getProductDescriptionName());
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




    public String getProductDescriptionName() {
        return productDescriptionName;
    }

    public void setProductDescriptionName(String productDescriptionName) {
        this.productDescriptionName = productDescriptionName;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}