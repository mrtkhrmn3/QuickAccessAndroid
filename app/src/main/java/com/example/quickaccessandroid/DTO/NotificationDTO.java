package com.example.quickaccessandroid.DTO;

public class NotificationDTO {
    public String id;
    public String userId;
    public String block;
    public String aptNo;
    public String type;
    public String status;
    public String description;
    public String createdAt;
    public String siteName;

    public NotificationDTO(String id, String userId, String block, String aptNo, String type, String status, String description, String createdAt, String siteName) {
        this.id = id;
        this.userId = userId;
        this.block = block;
        this.aptNo = aptNo;
        this.type = type;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
        this.siteName = siteName;
    }

    public String getNotificationId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getBlock() {
        return block;
    }

    public String getAptNo() {
        return aptNo;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setCreatedAt(String time){
        this.createdAt = time;
    }
}
