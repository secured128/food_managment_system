
package com.elalex.food.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;

//@JsonPropertyOrder({"unit_name"})
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "units")
public class UnitsDB implements Serializable
{
    @JsonIgnore
    @Transient
    private ObjectMapper jsonMapper = new ObjectMapper();

    @JsonProperty("units")
    @Id
    private String units;

    public UnitsDB(  String dbStructure[])
    {
        this.setUnits(dbStructure[0]);
    }

    public UnitsDB(String json) throws Exception
    {
        UnitsDB unitsDB = jsonMapper.readValue(json, UnitsDB.class);
        this.setUnits(unitsDB.getUnits());
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

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}