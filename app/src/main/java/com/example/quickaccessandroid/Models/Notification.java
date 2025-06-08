package com.example.quickaccessandroid.Models;

public class Notification {
    private String notificationId;
    private String title;
    private String message;
    private String timestamp;

    // Constructor
    public Notification(String notificationId, String title, String message, String timestamp) {
        this.notificationId = notificationId;
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

    public String getNotificationId(){
        return notificationId;
    }
}

