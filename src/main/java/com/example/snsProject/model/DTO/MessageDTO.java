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
public class MessageDTO {
    private long id;
    private long member_id;
    private long room_id;
    private Timestamp DATE;
    private int TYPE;
}
