package com.elalex.food.model;

public class ProductsPlacement
{
    private Long productId;
    private String productDescriptionName;
    private Double quantity;
    private String unit;
    private String placement;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductDescriptionName() {
        return productDescriptionName;
    }

    public void setProductDescriptionName(String productDescriptionName) {
        this.productDescriptionName = productDescriptionName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }
}
