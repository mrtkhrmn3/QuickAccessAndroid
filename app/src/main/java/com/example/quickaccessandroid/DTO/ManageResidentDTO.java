package com.example.quickaccessandroid.DTO;

public class ManageResidentDTO {
    private String id;
    private String name;
    private String surname;
    private String username;
    private String block;
    private String aptNo;
    private String phoneNumber;

    public ManageResidentDTO(String id, String name, String surname, String username, String block, String aptNo, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.block = block;
        this.aptNo = aptNo;
        this.phoneNumber = phoneNumber;
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

    public String getBlock() {
        return block;
    }

    public String getAptNo() {
        return aptNo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
