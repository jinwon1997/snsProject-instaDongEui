package com.example.snsProject.model.DTO;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.List;

@Data
@Repository
public class PostAllDTO {
    Long id;
    Long memberId;
    String content;
    String date;
    String userName;
    String name;
    String email;
    String url;
    List<PostImageDTO> images;
    List<PostLikeDTO> likes;
    List<PostTagDTO> tags;
    List<CommentViewDTO> comments;
    int commentSize;
    int likeYN;
    int bookmarkYN;
}
