package com.example.quickaccessandroid.DTO;

public class ResidentRegisterDTO {
    public String username;
    public String password;
    public String name;
    public String surname;
    public String block;
    public String aptNo;
    public String phoneNumber;
    public String siteName;

    public ResidentRegisterDTO(String name, String surname, String block, String aptNo, String phoneNumber, String siteName, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.block = block;
        this.aptNo = aptNo;
        this.phoneNumber = phoneNumber;
        this.siteName = siteName;
        this.username = username;
        this.password = password;
    }
}
