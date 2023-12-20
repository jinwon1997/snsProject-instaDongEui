package com.example.snsProject.model.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocialUsers {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String url;
    private String provider;

    @Builder
    public SocialUsers(Long id, String name, String email, String password, String provider, String url) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.url = url;
        this.password = password;
        this.provider = provider;
    }

    public SocialUsers update(String name, String provider) {
        this.name = name;
        this.provider = provider;
        return this;
    }

    public SocialUsers kakaoUpdate(String email) {
        this.email = email;
        return this;
    }
}