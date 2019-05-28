package com.elalex.food.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Date;



@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "transaction_log")


public class TransactionLogDB implements Serializable
{
   // static public final  int NUMBER_OF_PARAMS=8;
    static public final  String  TRANS_TYPE_GET_RECIPE_STOCK="GETSTOCK";
    @JsonIgnore
    @Transient
    private ObjectMapper jsonMapper = new ObjectMapper();

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonProperty("transactionId")
    @Column (name = "transaction_id")
    private long transactionId;


    @JsonProperty("userEmail")
    @Column(name = "user_email")
    private String userEmail;

    @JsonProperty("transactionType")
    @Column(name = "transaction_type")
    private String transactionType;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @JsonProperty("recipeName")
    @Column(name = "recipe_name")
    private String recipeName;

    @JsonProperty("recipeQuantity")
    @Column(name = "recipe_quantity")
    private Double recipeQuantity;

    @JsonProperty("recipeUnit")
    @Column(name = "recipe_unit")
    private String recipeUnit;


    @JsonProperty("stockId")
    @Column(name = "stock_id")
    private long stockId;

    @JsonProperty("usedQuantity")
    @Column(name = "used_quantity")
    private Double usedQuantity;

    @JsonProperty("cancelInd")
    @Column(name = "cancel_ind")
    private String cancelInd;

    @JsonProperty("cancellationTime")
    @Column(name = "cancellation_time")
    private LocalDateTime cancellationTime;


    public TransactionLogDB()
    {

    }


    public TransactionLogDB(String json) throws Exception
    {
        TransactionLogDB transactionLogDB = getJsonMapper().readValue(json, TransactionLogDB.class);
        this.setTransactionId(transactionLogDB.getTransactionId());
        this.setUserEmail(transactionLogDB.getUserEmail());
        this.setTransactionType(transactionLogDB.getTransactionType());
        this.setCreationDate(transactionLogDB.getCreationDate())  ;
        this.setRecipeName(transactionLogDB.getRecipeName());
        this.setStockId( transactionLogDB.getStockId());
        this.setUsedQuantity( transactionLogDB.getUsedQuantity());
        this.setRecipeQuantity(transactionLogDB.getRecipeQuantity());
        this.setRecipeUnit(transactionLogDB.getRecipeUnit());
        this.setCancelInd(transactionLogDB.getCancelInd());
        this.setCancellationTime(transactionLogDB.getCancellationTime());
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

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
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

    public String getCancelInd() {
        return cancelInd;
    }

    public void setCancelInd(String cancelInd) {
        this.cancelInd = cancelInd;
    }

    public LocalDateTime getCancellationTime() {
        return cancellationTime;
    }

    public void setCancellationTime(LocalDateTime cancellationTime) {
        this.cancellationTime = cancellationTime;
    }
}