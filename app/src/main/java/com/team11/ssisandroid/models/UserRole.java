package com.team11.ssisandroid.models;


public class UserRole {
    private String email;
    private String roleName;
    private String departmentId;

    public UserRole(String email, String roleName, String departmentId) {
        this.email = email;
        this.roleName = roleName;
        this.departmentId = departmentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}

