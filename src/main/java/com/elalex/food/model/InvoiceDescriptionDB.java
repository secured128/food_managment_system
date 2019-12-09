
package com.elalex.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@Entity
@Table(
        name = "invoice_description"
)
public class InvoiceDescriptionDB implements Serializable {
    public static final int NUMBER_OF_PARAMS = 4;
    public static final int IMAGE_COLUMN = 4;
    @JsonIgnore
    @Transient
    private ObjectMapper jsonMapper = new ObjectMapper();
    @JsonProperty("id")
    @Id
    @Column(
            name = "id"
    )
    private Long id;
    @JsonProperty("supplierName")
    @Column(
            name = "supplier_name"
    )
    private String supplierName;
    @JsonProperty("invoiceId")
    @Column(
            name = "invoice_id"
    )
    private String invoiceId;
    @JsonProperty("invoiceImage")
    @Column(
            name = "invoice_image"
    )
    private String invoiceImage;

    public InvoiceDescriptionDB() {
    }

    public InvoiceDescriptionDB(String json) throws Exception {
        InvoiceDescriptionDB invoiceDescriptionDB = (InvoiceDescriptionDB)this.getJsonMapper().readValue(json, InvoiceDescriptionDB.class);
        this.setId(invoiceDescriptionDB.getId());
        this.setSupplierName(invoiceDescriptionDB.getSupplierName());
        this.setInvoiceId(invoiceDescriptionDB.getInvoiceId());
        this.setInvoiceImage(invoiceDescriptionDB.getInvoiceImage());
    }

    public String toString() {
        StringWriter sw = new StringWriter();

        try {
            (new ObjectMapper()).writeValue(sw, this);
            return sw.toString();
        } catch (IOException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public ObjectMapper getJsonMapper() {
        return this.jsonMapper;
    }

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSupplierName() {
        return this.supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getInvoiceId() {
        return this.invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceImage() {
        return this.invoiceImage;
    }

    public void setInvoiceImage(String invoiceImage) {
        this.invoiceImage = invoiceImage;
    }
}
