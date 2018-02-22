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

@JsonPropertyOrder({"id", "name", "password"})
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users", indexes = {@Index(name = "user_unique_idx", columnList = "name", unique = true)})
public class User implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;

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

    @JsonProperty("password")
    @Column(name = "password")
    private String password;

    protected User() {
    }

    public User(String name, String password) {
        this.setName(name);
        this.setPassword(password);
    }

    public User(String json) throws Exception {
        User user = jsonMapper.readValue(json, User.class);
        this.setName(user.getName());
        this.setPassword(user.getPassword());
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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