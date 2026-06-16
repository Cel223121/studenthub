package com.example.studenthub.models;

public class OnlineStudent {
    private String name;
    private String username;

    public OnlineStudent(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }
}