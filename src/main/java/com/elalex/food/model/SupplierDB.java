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
@Table(name = "suppliers", indexes = {@Index(name = "Supplier_unique_idx", columnList = "name", unique = true)})
public class SupplierDB implements Serializable
{

    private static final long serialVersionUID = -3009157732242241505L;

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

    @JsonProperty("address")
    @Column(name = "address")
    private String address;

    @JsonProperty("city")
    @Column(name = "city")
    private String city;

    @JsonProperty("telephone_num")
    @Column(name = "telephone_num")
    private String telephone;

    @JsonProperty("fax")
    @Column(name = "fax")
    private String fax;

    @JsonProperty("email")
    @Column(name = "email")
    private String email;

    @JsonProperty("company_no")
    @Column(name = "company_no")
    private String companyNo;


    protected SupplierDB()
    {
    }

    public SupplierDB(String name, String address, String telephone, String fax, String email, String companyNo)
    {
        this.setName(name);
        this.setAddress(address);
        this.setCity(city);
        this.setTelephone(telephone);
        this.setFax(fax);
        this.setEmail(email);
        this.setCompanyNo(companyNo);
    }

    public SupplierDB(String json) throws Exception
    {
        SupplierDB supplierDB = jsonMapper.readValue(json, SupplierDB.class);
        this.setName(supplierDB.getName());
        this.setAddress(supplierDB.getAddress());
        this.setCity(supplierDB.getCity());
        this.setTelephone(supplierDB.getTelephone());
        this.setFax(supplierDB.getFax());
        this.setEmail(supplierDB.getEmail());
        this.setCompanyNo(supplierDB.getCompanyNo());
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() { return city; }
    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() { return fax; }
    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyNo() { return companyNo; }
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
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