
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
@Table(name = "instructions_description")
public class InstructionsDescriptionDB implements Serializable
{
    @JsonIgnore
    @Transient
    private ObjectMapper jsonMapper = new ObjectMapper();

    @JsonProperty("instructName")
    @Id
    private String instructName;

    @JsonProperty("instructionDescription")
    @Column(name = "instruction_description")
    private String instructionDescription;

    @JsonProperty("image")
    @Column(name = "image")
    private byte[] image;



    public InstructionsDescriptionDB(  String dbStructure[])
    {
        this.setInstructName(dbStructure[0]);
        this.setInstructionDescription(dbStructure[1]);
//        this.setImage(Long.parseLong(dbStructure[2]));
    }

    public InstructionsDescriptionDB(String json) throws Exception
    {
        InstructionsDescriptionDB instructionsDescriptionDB = jsonMapper.readValue(json, InstructionsDescriptionDB.class);
        this.setInstructName(instructionsDescriptionDB.getInstructName());
        this.setInstructionDescription(instructionsDescriptionDB.getInstructionDescription());
        this.setImage(instructionsDescriptionDB.getImage());
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    public String getInstructName() {
        return instructName;
    }

    public void setInstructName(String instructName) {
        this.instructName = instructName;
    }

    public String getInstructionDescription() {
        return instructionDescription;
    }

    public void setInstructionDescription(String instructionDescription) {
        this.instructionDescription = instructionDescription;
    }
}