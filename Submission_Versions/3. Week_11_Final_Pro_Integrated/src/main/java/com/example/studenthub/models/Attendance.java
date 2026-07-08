package com.example.studenthub.models;

public class Attendance {

    private int studentId;
    private String studentName;
    private String date;
    private String status;

    public Attendance(int studentId, String studentName, String date, String status) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.date = date;
        this.status = status;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}