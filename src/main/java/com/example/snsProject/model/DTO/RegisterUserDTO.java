package com.example.snsProject.model.DTO;

import lombok.Data;

@Data
public class RegisterUserDTO {

    private String userName;
    private String password;
    private String name;
    private String email;
    private String phone;

    public void createUser(String userName, String password, String name, String phoneOrEmail) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        if (phoneOrEmail.contains("@")) {
            this.email = phoneOrEmail;
        } else {
            this.phone = phoneOrEmail;
        }
    }
}
