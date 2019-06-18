package com.elalex.food.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

//@JsonPropertyOrder({"id", "name", "address","city", })

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "stock")

/*
  id bigint NOT NULL,
      supplier_name  character varying(255)NOT NULL,
      invoice_id character varying(255) NOT NULL,
      product_id bigint NOT NULL,
      quantity_in_package numeric NOT NULL,
      number_of_packages numeric NOT NULL,
      package_unit character varying(255) NOT NULL,
      price_per_package numeric NOT NULL,
      total_price numeric NOT NULL,
      quantity_for_recipes numeric NOT NULL,
      creation_date date not null,
      expiration_date date not null,
      placement character varying(255) NOT NULL,
      used_quantity numeric  NOT NULL,

 */


public class StockDB implements Serializable
{
    static public final  int NUMBER_OF_PARAMS=8;
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

    @JsonProperty("productId")
    @Column(name = "product_id")
    private long productId;

    @JsonProperty("quantityInPackage")
    @Column(name = "quantity_in_package")
    private Double quantityInPackage;

    @JsonProperty("numberOfPackages")
    @Column(name = "number_of_packages")
    private Double numberOfPackages;

    @JsonProperty("packageUnit")
    @Column(name = "package_unit")
    private String packageUnit;

    @JsonProperty("pricePerPackage")
    @Column(name = "price_per_package")
    private Double pricePerPackage;

    @JsonProperty("totalPrice")
    @Column(name = "total_price")
    private Double totalPrice;

    @JsonProperty("quantityForRecipes")
    @Column(name = "quantity_for_recipes")
    private Double quantityForRecipes;

    @JsonProperty("creationDate")
    @Column(name = "creation_date")
    private Date creationDate;

    @JsonProperty("expirationDate")
    @Column(name = "expiration_date")
    private Date expirationDate;

    @JsonProperty("placement")
    @Column(name = "placement")
    private String placement;

    @JsonProperty("usedQuantity")
    @Column(name = "used_quantity")
    private Double usedQuantity;



    public StockDB()
    {

    }
    public StockDB(  String dbStructure[])throws Exception
    {
        this.setSupplierName(dbStructure[0]);
        this.setInvoiceId(dbStructure[1]);
        this.setProductId((long) Double.parseDouble(dbStructure[2]));
        this.setQuantityInPackage(Double.valueOf(dbStructure[3]));
        this.setNumberOfPackages(Double.valueOf(dbStructure[4]));
        this.setPackageUnit(dbStructure[5]);
        this.setPricePerPackage(Double.valueOf(dbStructure[6]));
        this.setTotalPrice(Double.valueOf(dbStructure[7]));
        this.setQuantityForRecipes(Double.valueOf(dbStructure[8]));
        this.setCreationDate(new SimpleDateFormat("dd/MM/yyyy").parse(dbStructure[9]))  ;
        this.setExpirationDate(new SimpleDateFormat("dd/MM/yyyy").parse(dbStructure[10]));
        this.setPlacement(dbStructure[11]);
        this.setUsedQuantity(Double.valueOf(dbStructure[12]));

    }

    public StockDB(String json) throws Exception
    {
        StockDB stockDB = getJsonMapper().readValue(json, StockDB.class);
        this.setId(stockDB.getId());
        this.setSupplierName(stockDB.getSupplierName());
        this.setInvoiceId(stockDB.getInvoiceId());
        this.setProductId(stockDB.getProductId());
        this.setQuantityInPackage(stockDB.getQuantityInPackage());
        this.setNumberOfPackages(stockDB.getNumberOfPackages());
        this.setPackageUnit(stockDB.getPackageUnit());
        this.setPricePerPackage(stockDB.getPricePerPackage());
        this.setTotalPrice(stockDB.getTotalPrice());
        this.setQuantityForRecipes(stockDB.getQuantityForRecipes());
        this.setCreationDate(stockDB.getCreationDate())  ;
        this.setExpirationDate(stockDB.getExpirationDate());
        this.setPlacement(stockDB.getPlacement());
        this.setUsedQuantity(stockDB.getUsedQuantity());

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

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }


    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Double getUsedQuantity() {
        return usedQuantity;
    }

    public void setUsedQuantity(Double usedQuantity) {
        this.usedQuantity = usedQuantity;
    }

    public Double getQuantityInPackage() {
        return quantityInPackage;
    }

    public void setQuantityInPackage(Double quantityInPackage) {
        this.quantityInPackage = quantityInPackage;
    }

    public Double getNumberOfPackages() {
        return numberOfPackages;
    }

    public void setNumberOfPackages(Double numberOfPackages) {
        this.numberOfPackages = numberOfPackages;
    }

    public String getPackageUnit() {
        return packageUnit;
    }

    public void setPackageUnit(String packageUnit) {
        this.packageUnit = packageUnit;
    }

    public Double getPricePerPackage() {
        return pricePerPackage;
    }

    public void setPricePerPackage(Double pricePerPackage) {
        this.pricePerPackage = pricePerPackage;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getQuantityForRecipes() {
        return quantityForRecipes;
    }

    public void setQuantityForRecipes(Double quantityForRecipes) {
        this.quantityForRecipes = quantityForRecipes;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}