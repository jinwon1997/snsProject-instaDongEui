package com.example.snsProject.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    Long id;
    String userName;
    String password;
    String name;
    String email;
    String introduce;
    String phone;
    Integer gender;
    String url;
    String uuid;
    String emoticon;
}
