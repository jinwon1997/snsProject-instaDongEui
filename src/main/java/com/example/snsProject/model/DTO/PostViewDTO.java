package com.example.snsProject.model.DTO;

import lombok.Data;

@Data
public class PostViewDTO {
    Long id;
    Long memberId;
    String content;
    String date;
    String userName;
    String name;
    String email;
    String url;
}
