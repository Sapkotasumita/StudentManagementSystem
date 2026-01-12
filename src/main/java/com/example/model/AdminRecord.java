package com.example.dsgroup1.model;

public class AdminRecord {
    private final String adminId;
    private final String adminName;
    private final String adminEmail;
    private final String adminPhone;

    public AdminRecord(String adminId, String adminName, String adminEmail, String adminPhone) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPhone = adminPhone;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public String getAdminPhone() {
        return adminPhone;
    }
}
