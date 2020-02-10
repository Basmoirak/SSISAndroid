package com.team11.ssisandroid.models;

import java.util.Date;

public class Delegation {
    private String userId;
    private String email;
    private String departmentId;
    private String departmentName;
    private Date startDate;
    private Date endDate;

    public Delegation(String userId, String email, String departmentId, String departmentName, Date startDate, Date endDate) {
        this.userId = userId;
        this.email = email;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
