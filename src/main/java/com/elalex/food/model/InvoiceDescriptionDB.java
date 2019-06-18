
package com.elalex.food.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;

/*
 id bigint NOT NULL,
  supplier_name character varying(255),
  invoice_id character varying(255),
  image bytea,
  CONSTRAINT invoice_description PRIMARY KEY (id)
 */
//@JsonPropertyOrder({"id"})
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "invoice_description")
public class InvoiceDescriptionDB implements Serializable
{
    static public final  int NUMBER_OF_PARAMS=4;//we put 4 and npt 5, since image is not uploaded from excel
    static public final  int IMAGE_COLUMN=4;
    @JsonIgnore
    @Transient
    private ObjectMapper jsonMapper = new ObjectMapper();


    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonProperty("supplierName")
    @Column(name = "supplier_name")
    private String supplierName;

    @JsonProperty("invoiceId")
    @Column(name = "invoice_id")
    private String invoiceId;

    @JsonProperty("image")
    @Column(name = "image")
    private byte[] image;


    public InvoiceDescriptionDB()
    {
    }

    public InvoiceDescriptionDB(String dbStructure[], HashMap<String, byte[]> picturesMap)
    {
        this.setSupplierName(dbStructure[0]);
        this.setInvoiceId(dbStructure[1]);
        if (picturesMap != null)
         this.setImage(picturesMap.get(dbStructure[0]));

    }

    public InvoiceDescriptionDB(String json) throws Exception
    {
        InvoiceDescriptionDB invoiceDescriptionDB = getJsonMapper().readValue(json, InvoiceDescriptionDB.class);
        this.setId(invoiceDescriptionDB.getId());
        this.setSupplierName(invoiceDescriptionDB.getSupplierName());
        this.setInvoiceId(invoiceDescriptionDB.getInvoiceId());
        this.setImage(invoiceDescriptionDB.getImage());
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}