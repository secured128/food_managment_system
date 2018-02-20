package com.elalex;

/**
 * Created by alexb on 2/20/2018.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by alexb on 2/5/2018.
 */
@JsonPropertyOrder({"id", "name"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private ObjectMapper jsonMapper = new ObjectMapper();


    @JsonProperty("id")
    private String id = StringUtils.EMPTY;

    @JsonProperty("name")
    private String name = StringUtils.EMPTY;


    public User() {
        super();
    }

    public User(String id, String name) {
        super();
        setId(id);
        setName(name);
        jsonMapper.setDefaultPrettyPrinter(new DefaultPrettyPrinter());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringWriter sw = new StringWriter();
        try {
            jsonMapper.writeValue(sw, this);
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;
        if (this.getId().equals(user.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
