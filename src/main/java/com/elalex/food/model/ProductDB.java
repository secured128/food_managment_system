
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
@Table(name = "product")
public class ProductDB implements Serializable
{
    static public final  int NUMBER_OF_PARAMS=7;
    @JsonIgnore
    @Transient
    private ObjectMapper jsonMapper = new ObjectMapper();

    @JsonProperty("id")
    @Id
    private Long id;

    @JsonProperty("code1")
    @Column(name = "code1")
    private Long code1;

    @JsonProperty("code2")
    @Column(name = "code2")
    private Long code2;

    @JsonProperty("description")
    @Column(name = "description")
    private String description;

    @JsonProperty("unit")
    @Column(name = "unit")
    private String unit;

    @JsonProperty("idSupplier")
    @Column(name = "idSupplier")
    private Long idSupplier;

    @JsonProperty("quantityInPackage")
    @Column(name = "quantity_in_package")
    private Double quantityInPackage;

    public ProductDB( )
    {
    }

    public ProductDB(  String dbStructure[])
    {
        this.setId ((long) Double.parseDouble(dbStructure[0]));
        this.setCode1((long) Double.parseDouble(dbStructure[1]));
        this.setCode2((long) Double.parseDouble(dbStructure[2]));
        this.setDescription(dbStructure[3]);
        this.setUnit(dbStructure[4]);
        this.setIdSupplier((long) Double.parseDouble(dbStructure[5]));
        this.setQuantityInPackage(Double.parseDouble(dbStructure[6]));

    }

    public ProductDB(String json) throws Exception
    {
        ProductDB productDB = jsonMapper.readValue(json, ProductDB.class);
        this.setId(productDB.getId());
        this.setCode1(productDB.getCode1());
        this.setCode2(productDB.getCode2());
        this.setDescription(productDB.getDescription());
        this.setUnit(productDB.getUnit());
        this.setIdSupplier(productDB.getIdSupplier());
        this.setQuantityInPackage(productDB.getQuantityInPackage());
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

    public Long getCode1() {
        return code1;
    }

    public void setCode1(Long code1) {
        this.code1 = code1;
    }

    public Long getCode2() {
        return code2;
    }

    public void setCode2(Long code2) {
        this.code2 = code2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(Long idSupplier) {
        this.idSupplier = idSupplier;
    }


    public Double getQuantityInPackage() {
        return quantityInPackage;
    }

    public void setQuantityInPackage(Double quantityInPackage) {
        this.quantityInPackage = quantityInPackage;
    }
}