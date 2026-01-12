package com.example.dsgroup1.model;

public class ReportRecord {
    private String reportId;
    private String reportName;
    private String reportContent;
    private String progress;
    private String grade;

    public ReportRecord(String reportId, String reportName, String reportContent, String progress, String grade) {
        this.reportId = reportId;
        this.reportName = reportName;
        this.reportContent = reportContent;
        this.progress = progress;
        this.grade = grade;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}

