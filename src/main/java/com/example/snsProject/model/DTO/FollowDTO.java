package com.example.snsProject.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FollowDTO {

    Long id;
    Long followId;
    Long memberId;

}
