package com.team11.ssisandroid.models;

public class StockAdjustment {
    private String stockAdjustmentId;
    private String itemId;
    private String createdBy;
    private String remarks;
    private Integer movement;

    public StockAdjustment(String stockAdjustmentId, String itemId, String createdBy, String remarks, Integer movement) {
        this.stockAdjustmentId = stockAdjustmentId;
        this.itemId = itemId;
        this.createdBy = createdBy;
        this.remarks = remarks;
        this.movement = movement;
    }

    public String getStockAdjustmentId() {
        return stockAdjustmentId;
    }

    public void setStockAdjustmentId(String stockAdjustmentId) {
        this.stockAdjustmentId = stockAdjustmentId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getMovement() {
        return movement;
    }

    public void setMovement(Integer movement) {
        this.movement = movement;
    }
}
