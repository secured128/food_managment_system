package com.elalex.food.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;

@JsonPropertyOrder({"id", "name"})
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Saler")
public class SalerDB implements Serializable
{

    private static final long serialVersionUID = -3009157732242241501L;

    @JsonIgnore
    @Transient
    private ObjectMapper jsonMapper = new ObjectMapper();

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonProperty("name")
    @Column(name = "name")
    private String name;

    @JsonProperty("telephone")
    @Column(name = "telephone")
    private String telephone;


    @JsonProperty("email")
    @Column(name = "email")
    private String email;

    @JsonProperty("supplierId")
    @Column(name = "supplierId")
    private String supplierId;


    protected SalerDB()
    {
    }

    public SalerDB(String name,  String telephone, String email, String supplierId)
    {
        this.setName(name);
        this.setTelephone(telephone);
        this.setEmail(email);
        this.setSupplierId(supplierId);
    }

    public SalerDB(String json) throws Exception
    {
        SalerDB salerDB = jsonMapper.readValue(json, SalerDB.class);
        this.setName(salerDB.getName());
        this.setTelephone(salerDB.getTelephone());
        this.setEmail(salerDB.getEmail());
        this.setSupplierId(salerDB.getCompanyNo());
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyNo() { return supplierId; }
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
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
}