package com.elalex.food.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;



@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "transaction_log")


public class TransactionLogDB implements Serializable
{
    static public final  int NUMBER_OF_PARAMS=8;
    @JsonIgnore
    @Transient
    private ObjectMapper jsonMapper = new ObjectMapper();

    @JsonProperty("transaction_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;

    @JsonProperty("userId")
    @Column(name = "user_id")
    private String userId;

    @JsonProperty("userEmail")
    @Column(name = "user_email")
    private String userEmail;

    @JsonProperty("transactionType")
    @Column(name = "transaction_type")
    private String transactionType;

    @JsonProperty("creationDate")
    @Column(name = "creation_date")
    private Date creationDate;

    @JsonProperty("recipeDescName")
    @Column(name = "recipe_desc_name")
    private String recipeDescName;

    @JsonProperty("recipeQuantity")
    @Column(name = "recipe_quantity")
    private Double recipeQuantity;

    @JsonProperty("stockId")
    @Column(name = "stock_id")
    private long stockId;

    @JsonProperty("usedQuantity")
    @Column(name = "used_quantity")
    private Double usedQuantity;


    public TransactionLogDB()
    {

    }
    public TransactionLogDB(  String dbStructure[])throws Exception
    {
        this.setTransactionId( Long.parseLong(dbStructure[0]));
        this.setUserId(dbStructure[1]);
        this.setUserEmail(dbStructure[2]);
        this.setTransactionType(dbStructure[3]);
        this.setCreationDate(new SimpleDateFormat("dd/MM/yyyy").parse(dbStructure[4]))  ;
        this.setRecipeDescName(dbStructure[5]);
        this.setRecipeQuantity(Double.valueOf(dbStructure[6]));
        this.setStockId( Long.parseLong(dbStructure[7]));
        this.setUsedQuantity( Double.parseDouble(dbStructure[8]));
    }

    public TransactionLogDB(String json) throws Exception
    {
        TransactionLogDB transactionLogDB = getJsonMapper().readValue(json, TransactionLogDB.class);
        this.setTransactionId(transactionLogDB.getTransactionId());
        this.setUserId(transactionLogDB.getUserId());
        this.setUserEmail(transactionLogDB.getUserEmail());
        this.setTransactionType(transactionLogDB.getTransactionType());
        this.setCreationDate(transactionLogDB.getCreationDate())  ;
        this.setRecipeDescName(transactionLogDB.getRecipeDescName());
        this.setRecipeQuantity(transactionLogDB.getRecipeQuantity());
        this.setStockId( transactionLogDB.getStockId());
        this.setUsedQuantity( transactionLogDB.getUsedQuantity());
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getRecipeDescName() {
        return recipeDescName;
    }

    public void setRecipeDescName(String recipeDescName) {
        this.recipeDescName = recipeDescName;
    }

    public Double getRecipeQuantity() {
        return recipeQuantity;
    }

    public void setRecipeQuantity(Double recipeQuantity) {
        this.recipeQuantity = recipeQuantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getStockId() {
        return stockId;
    }

    public void setStockId(long stockId) {
        this.stockId = stockId;
    }

    public Double getUsedQuantity() {
        return usedQuantity;
    }

    public void setUsedQuantity(Double usedQuantity) {
        this.usedQuantity = usedQuantity;
    }
}