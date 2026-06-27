package com.example.studenthub.models;

public class Student {
    private String id;
    private String name;
    private String email;
    private String course;
    private int year;
    private String phone;

    public Student(String id, String name, String email, String course, int year, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
        this.year = year;
        this.phone = phone;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCourse() { return course; }
    public int getYear() { return year; }
    public String getPhone() { return phone; }
}