package com.example.quickaccessandroid.Models;

public class Notification {
    private String title;
    private String message;
    private String timestamp;

    // Constructor
    public Notification(String title, String message, String timestamp) {
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}

