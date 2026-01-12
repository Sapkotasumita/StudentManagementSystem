package com.example.dsgroup1.model;

public class StudentProblemRecord {
    private String problemId;
    private String title;
    private String scId;
    private String creatorName;
    private String questionId;
    private String questionPosition;
    private String questionText;

    public StudentProblemRecord(String problemId, String title, String scId, String creatorName, String questionId, String questionPosition, String questionText) {
        this.problemId = problemId;
        this.title = title;
        this.scId = scId;
        this.creatorName = creatorName;
        this.questionId = questionId;
        this.questionPosition = questionPosition;
        this.questionText = questionText;
    }

    // Getters and Setters
    public String getProblemId() { return problemId; }
    public void setProblemId(String problemId) { this.problemId = problemId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getScId() { return scId; }
    public void setScId(String scId) { this.scId = scId; }

    public String getCreatorName() { return creatorName; }
    public void setCreatorName(String creatorName) { this.creatorName = creatorName; }

    public String getQuestionId() { return questionId; }
    public void setQuestionId(String questionId) { this.questionId = questionId; }

    public String getQuestionPosition() { return questionPosition; }
    public void setQuestionPosition(String questionPosition) { this.questionPosition = questionPosition; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
}

