package com.example.quickaccessandroid.DTO;

public class CreateNotificationDTO {
    public String type;
    public String description;

    public CreateNotificationDTO(String type, String description) {
        this.type = type;
        this.description = description;
    }
}
