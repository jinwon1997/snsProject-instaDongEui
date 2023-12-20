package com.example.snsProject.model.DTO;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CommentViewDTO {
    Long id;
    Long memberId;
    String content;
    String date;
    Long postId;
    Long parentId;
    String userName;
    String name;
    String email;
    String url;
}
