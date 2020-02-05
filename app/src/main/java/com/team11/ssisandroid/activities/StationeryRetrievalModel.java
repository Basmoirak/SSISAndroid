package com.team11.ssisandroid.activities;

import java.util.List;

public class StationeryRetrievalModel {

    private String itemID;
    private String itemCode;
    private String itemDescription;
    private int requestedQuantity;
    private int availableQuantity;


    public StationeryRetrievalModel(String itemID, String itemCode, String itemDescription, int requestedQuantity, int availableQuantity) {
        this.itemID = itemID;
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;

    }

    public String getItemID() {
        return itemID;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }


}
