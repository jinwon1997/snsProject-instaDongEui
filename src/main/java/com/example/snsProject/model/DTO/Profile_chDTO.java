package com.example.snsProject.model.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
@Component
public class Profile_chDTO {
    private long follow_member_id;
    private long member_id;
    private long url;
    private String introduce;
    private int gender;
    private String user_name;
}
