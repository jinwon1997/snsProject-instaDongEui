package com.example.snsProject.model.DTO;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class Member {

    private Long id;
    private String userName;
    private String phone;
    private String email;
    private String password;

    private Member(Long id, String userid, String pw) {
        this.id = id;
        this.email = userid;
        this.phone = userid;
        this.userName = userid;
        this.password = pw;
    }

    protected Member() {}

    public static Member createUser(String name, String password, PasswordEncoder passwordEncoder) {
        return new Member(null, name, passwordEncoder.encode(password));
    }

}
