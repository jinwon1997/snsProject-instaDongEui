package com.example.snsProject.model.DTO;


import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;


@Getter
@Setter
@Mapper
@Component
public class MessageTextDTO {
    private long message_id;
    private String content;
}