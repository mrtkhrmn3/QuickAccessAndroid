package com.example.quickaccessandroid.DTO;

public class SecurityRegisterDTO {
    public String name;
    public String surname;
    public String siteName;
    public String username;
    public String password;

    public SecurityRegisterDTO(String name, String surname, String siteName, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.siteName = siteName;
        this.username = username;
        this.password = password;
    }
}
