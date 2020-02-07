package com.team11.ssisandroid.models;

public class CollectionDetails {
    private String itemID;
    private String itemCode;
    private String itemDescription;
    private int requestedQuantity;
    private int availableQuantity;

    public CollectionDetails(String itemID, String itemCode, String itemDescription, int requestedQuantity, int availableQuantity) {
        this.itemID = itemID;
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
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

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}
