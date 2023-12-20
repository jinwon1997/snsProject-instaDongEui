package com.example.snsProject.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookmarkDTO {

    long id;
    long memberId;
    long postId;
}
