package com.example.dsgroup1.model;

public class StudentRecord {
    private String studentId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String faculty;
    private String email;
    private String phoneNo;
    private String gender;

    public StudentRecord(String studentId, String username, String password, String firstName, String lastName, String faculty, String email, String phoneNo, String gender) {
        this.studentId = studentId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.faculty = faculty;
        this.email = email;
        this.phoneNo = phoneNo;
        this.gender = gender;
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}

