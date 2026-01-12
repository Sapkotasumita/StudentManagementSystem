package com.example.dsgroup1.model;

public class CounselingRecord {
    private String studentId;
    private String counselor;
    private String date;

    public CounselingRecord(String studentId, String counselor, String date) {
        this.studentId = studentId;
        this.counselor = counselor;
        this.date = date;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCounselor() {
        return counselor;
    }

    public void setCounselor(String counselor) {
        this.counselor = counselor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
