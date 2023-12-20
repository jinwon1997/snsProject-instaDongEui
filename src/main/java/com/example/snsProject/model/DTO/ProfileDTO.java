package com.example.snsProject.model.DTO;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Getter
@Setter
@Component
public class ProfileDTO {
    private long follow_member_id;
    private long member_id;
    private long url;
    private String introduce;
}
