package com.example.quickaccessandroid.DTO;

public class ManageSecurityDTO {
    private String id;
    private String name;
    private String surname;
    private String username;
    private String siteName;

    public ManageSecurityDTO(String id, String name, String surname, String username, String siteName) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.siteName = siteName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getSiteName() {
        return siteName;
    }
}
