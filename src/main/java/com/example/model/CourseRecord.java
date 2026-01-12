package com.example.dsgroup1.model;

public class CourseRecord {
    private String courseId;
    private String courseName;
    private String instructor;
    private String courseDuration;
    private String courseType;

    public CourseRecord(String courseId, String courseName, String instructor, String courseDuration, String courseType) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructor = instructor;
        this.courseDuration = courseDuration;
        this.courseType = courseType;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }
}
