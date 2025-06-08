package com.example.quickaccessandroid.DTO;

public class SiteManagerRegisterDTO {
    public String username;
    public String password;
    public String name;
    public String surname;
    public String siteName;

    public SiteManagerRegisterDTO( String username, String password, String name, String surname, String siteName) {
        this.name = name;
        this.surname = surname;
        this.siteName = siteName;
        this.username = username;
        this.password = password;
    }
}
