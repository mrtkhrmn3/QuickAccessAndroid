package com.example.quickaccessandroid.DTO;

public class ManageAdminDTO {
    private String id;
    private String name;
    private String surname;
    private String username;
    private String role;

    public ManageAdminDTO(String name, String surname, String username, String role) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.role = role;
    }

    public String getId(){
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

    public String getRole() {
        return role;
    }
}
