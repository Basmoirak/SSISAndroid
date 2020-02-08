package com.team11.ssisandroid.models;

public class StoreCollection {
    private DepartmentCollection[] groupedDepartmentCollections;

    public StoreCollection(DepartmentCollection[] groupedDepartmentCollections) {
        this.groupedDepartmentCollections = groupedDepartmentCollections;
    }

    public DepartmentCollection[] getGroupedDepartmentCollections() {
        return groupedDepartmentCollections;
    }

    public void setGroupedDepartmentCollections(DepartmentCollection[] groupedDepartmentCollections) {
        this.groupedDepartmentCollections = groupedDepartmentCollections;
    }
}
