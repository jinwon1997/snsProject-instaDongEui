package com.example.snsProject.model.DTO;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CommentDTO {
    Long id;
    @NonNull
    Long memberId;
    @NonNull
    Long postId;
    Long parentId;
    @NonNull
    String content;
    String date;
}
