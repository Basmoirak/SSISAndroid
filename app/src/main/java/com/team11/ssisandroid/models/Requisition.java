package com.team11.ssisandroid.models;

public class Requisition {
    private String requisitionId;
    private String remarks;
    private Integer status;
    private RequisitionDetail[] itemRequisitions;

    public Requisition(String requisitionId, String remarks, Integer status, RequisitionDetail[] itemRequisitions) {
        this.requisitionId = requisitionId;
        this.remarks = remarks;
        this.status = status;
        this.itemRequisitions = itemRequisitions;
    }

    public String getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(String requisitionId) {
        this.requisitionId = requisitionId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public RequisitionDetail[] getItemRequisitions() {
        return itemRequisitions;
    }

    public void setItemRequisitions(RequisitionDetail[] itemRequisitions) {
        this.itemRequisitions = itemRequisitions;
    }
}
