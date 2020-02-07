package com.team11.ssisandroid.models;

public class RequisitionDetail {
    private String itemCode;
    private String description;
    private Integer quantity;

    public RequisitionDetail(String itemCode, String description, Integer quantity) {
        this.itemCode = itemCode;
        this.description = description;
        this.quantity = quantity;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getDescription() {
        return description;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
