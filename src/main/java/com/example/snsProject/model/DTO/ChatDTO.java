package com.example.snsProject.model.DTO;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Mapper
@Component
public class ChatDTO {
    private long id;
    private String name;
    private String user_name;
    private String url;
    private String email;
    private String phone;
    private String introduce;
}
