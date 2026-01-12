package com.example.dsgroup1.model;

public class QuestionRecord {
    private String questionId;
    private String questionText;
    private String questionDescription;

    public QuestionRecord(String questionId, String questionText, String questionDescription) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionDescription = questionDescription;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }
}

