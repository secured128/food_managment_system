
package com.elalex.food.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;



@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "transaction_log")


public class TransactionLogSelectCreatedRecipesDB implements Serializable
{

    @JsonIgnore
    @Transient
    private ObjectMapper jsonMapper = new ObjectMapper();

    @JsonProperty("transactionId")
    @Id
    @Column (name = "transaction_id")
    private long transactionId;


    @JsonProperty("userEmail")
    @Column(name = "user_email")
    private String userEmail;

    @JsonProperty ("creationDate")
    @Column(name = "creation_date")
    private String creationDate;

    @JsonProperty("recipeName")
    @Column(name = "recipe_name")
    private String recipeName;

    @JsonProperty("recipeQuantity")
    @Column(name = "recipe_quantity")
    private Double recipeQuantity;

    @JsonProperty("recipeUnit")
    @Column(name = "recipe_unit")
    private String recipeUnit;


    public TransactionLogSelectCreatedRecipesDB()
    {

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

    public ObjectMapper getJsonMapper() {
        return jsonMapper;
    }

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }


    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }



    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }


    public Double getRecipeQuantity() {
        return recipeQuantity;
    }

    public void setRecipeQuantity(Double recipeQuantity) {
        this.recipeQuantity = recipeQuantity;
    }

    public String getRecipeUnit() {
        return recipeUnit;
    }

    public void setRecipeUnit(String recipeUnit) {
        this.recipeUnit = recipeUnit;
    }
}
