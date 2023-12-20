package com.example.snsProject.model.DTO;

import lombok.Data;

@Data
public class PostImageDTO {
    Long id;
    Long postId;
    String url;
    String uuid;
}
