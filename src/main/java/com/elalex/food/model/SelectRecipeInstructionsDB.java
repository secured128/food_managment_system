package com.elalex.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;

@Entity
public class SelectRecipeInstructionsDB implements Serializable
{

    @JsonIgnore
    @Transient
    private ObjectMapper jsonMapper = new ObjectMapper();

    @JsonProperty("idField")
    @Id
    @Column(name = "id_field")
    private String idField;

    @JsonProperty("recipeDescriptionName")
    @Column(name = "recipe_description_name")
    private String recipeDescriptionName;




    @JsonProperty("linkedRecipeDescName")
    @Column(name = "linked_recipe_desc_name")
    private String linkedRecipeDescName;

    @JsonProperty("instructionName")
    @Column(name = "instruction_name")
    private String instructionName;

    @JsonProperty("level")
    @Column(name = "level")
    private Integer level;

    @JsonProperty("instructionOrder")
    @Column(name = "instruction_order")
    private Integer instructionOrder;


    @JsonProperty("instructionDescription")
    @Column(name = "instruction_description")
    private String instructionDescription;

    public SelectRecipeInstructionsDB()
    {

    }

    public SelectRecipeInstructionsDB(String json) throws Exception
    {
        SelectRecipeInstructionsDB selectRecipeInstructionsDB = jsonMapper.readValue(json, SelectRecipeInstructionsDB.class);
        this.setIdField(selectRecipeInstructionsDB.getIdField());
        this.setRecipeDescriptionName(selectRecipeInstructionsDB.getRecipeDescriptionName());
        this.setLinkedRecipeDescName(selectRecipeInstructionsDB.getLinkedRecipeDescName());
        this.setInstructionName(selectRecipeInstructionsDB.getInstructionName());
        this.setLevel(selectRecipeInstructionsDB.getLevel());
        this.setInstructionOrder(selectRecipeInstructionsDB.getInstructionOrder());
        this.setInstructionDescription(selectRecipeInstructionsDB.getInstructionDescription());
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


    public String getRecipeDescriptionName() {
        return recipeDescriptionName;
    }

    public void setRecipeDescriptionName(String recipeDescriptionName) {
        this.recipeDescriptionName = recipeDescriptionName;
    }

    public String getLinkedRecipeDescName() {
        return linkedRecipeDescName;
    }

    public void setLinkedRecipeDescName(String linkedRecipeDescName) {
        this.linkedRecipeDescName = linkedRecipeDescName;
    }

    public String getInstructionName() {
        return instructionName;
    }

    public void setInstructionName(String instructionName) {
        this.instructionName = instructionName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getInstructionOrder() {
        return instructionOrder;
    }

    public void setInstructionOrder(Integer instructionOrder) {
        this.instructionOrder = instructionOrder;
    }

    public String getInstructionDescription() {
        return instructionDescription;
    }

    public void setInstructionDescription(String instructionDescription) {
        this.instructionDescription = instructionDescription;
    }

    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }
}
