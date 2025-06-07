package com.example.quickaccessandroid.DTO;

public class AdminRegisterDTO {
    public String username;
    public String password;
    public String name;
    public String surname;

    public AdminRegisterDTO(String name, String surname, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
    }
}
