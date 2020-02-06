package com.team11.ssisandroid.models;

public class RequisitionDetail {
    private String ItemCode;
    private String Description;
    private Integer Quantity;

    public RequisitionDetail(String itemCode, String description, Integer quantity) {
        ItemCode = itemCode;
        Description = description;
        Quantity = quantity;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }
}
