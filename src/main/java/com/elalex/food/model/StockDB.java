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

//@JsonPropertyOrder({"id", "name", "address","city", })

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "stock")




public class StockDB implements Serializable
{
    static public final  int NUMBER_OF_PARAMS=8;
    @JsonIgnore
    @Transient
    private ObjectMapper jsonMapper = new ObjectMapper();

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonProperty("productId")
    @Column(name = "product_id")
    private long productId;

    @JsonProperty("quantity")
    @Column(name = "quantity")
    private Double quantity;

    @JsonProperty("price")
    @Column(name = "price")
    private Double price;

    @JsonProperty("creationDate")
    @Column(name = "creation_date")
    private Date creationDate;

    @JsonProperty("expirationDate")
    @Column(name = "expiration_date")
    private Date expirationDate;

    @JsonProperty("placement")
    @Column(name = "placement")
    private String placement;

    @JsonProperty("usedQuantity")
    @Column(name = "used_quantity")
    private Double usedQuantity;



    public StockDB()
    {

    }
    public StockDB(  String dbStructure[])throws Exception
    {
        this.setId((long) Double.parseDouble(dbStructure[0]));
        this.setProductId((long) Double.parseDouble(dbStructure[1]));
        this.setQuantity(Double.valueOf(dbStructure[2]));
        this.setPrice(Double.valueOf(dbStructure[3]));
        this.setCreationDate(new SimpleDateFormat("dd/MM/yyyy").parse(dbStructure[4]))  ;
        this.setExpirationDate(new SimpleDateFormat("dd/MM/yyyy").parse(dbStructure[5]));
        this.setPlacement(dbStructure[6]);
        this.setUsedQuantity(Double.valueOf(dbStructure[7]));

    }

    public StockDB(String json) throws Exception
    {
        StockDB stockDB = getJsonMapper().readValue(json, StockDB.class);
        this.setId(stockDB.getId());
        this.setProductId(stockDB.getProductId());
        this.setQuantity(stockDB.getQuantity());
        this.setPrice(stockDB.getPrice());
        this.setCreationDate(stockDB.getCreationDate())  ;
        this.setExpirationDate(stockDB.getExpirationDate());
        this.setPlacement(stockDB.getPlacement());
        this.setUsedQuantity(stockDB.getUsedQuantity());

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }



    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Double getUsedQuantity() {
        return usedQuantity;
    }

    public void setUsedQuantity(Double usedQuantity) {
        this.usedQuantity = usedQuantity;
    }

}