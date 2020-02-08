package com.team11.ssisandroid.models;

public class Collection {
    private String departmentId;
    private String departmentName;
    private String collectionPoint;
    private CollectionDetails[] itemDisbursements;

    public Collection(String departmentId, String departmentName, String collectionPoint, CollectionDetails[] itemDisbursements) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.collectionPoint = collectionPoint;
        this.itemDisbursements = itemDisbursements;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(String collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    public CollectionDetails[] getItemDisbursements() {
        return itemDisbursements;
    }

    public void setItemDisbursements(CollectionDetails[] itemDisbursements) {
        this.itemDisbursements = itemDisbursements;
    }
}
