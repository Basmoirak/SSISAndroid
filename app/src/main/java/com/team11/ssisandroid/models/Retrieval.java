package com.team11.ssisandroid.models;

public class Retrieval {
    private String itemId;
    private String itemCode;
    private String itemDescription;
    private Integer requestedQuantity;
    private Integer availableQuantity;

    public Retrieval(String itemId, String itemCode, String itemDescription, Integer requestedQuantity, Integer availableQuantity) {
        this.itemId = itemId;
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(Integer requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}
