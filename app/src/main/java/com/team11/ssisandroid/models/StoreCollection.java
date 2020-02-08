package com.team11.ssisandroid.models;

public class StoreCollection {
    private Collection[] groupedDepartmentCollections;

    public StoreCollection(Collection[] groupedDepartmentCollections) {
        this.groupedDepartmentCollections = groupedDepartmentCollections;
    }

    public Collection[] getGroupedDepartmentCollections() {
        return groupedDepartmentCollections;
    }

    public void setGroupedDepartmentCollections(Collection[] groupedDepartmentCollections) {
        this.groupedDepartmentCollections = groupedDepartmentCollections;
    }
}
