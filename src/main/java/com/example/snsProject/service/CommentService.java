package com.example.snsProject.service;


import com.example.snsProject.model.DAO.CommentDAO;
import com.example.snsProject.model.DTO.CommentDTO;
import com.example.snsProject.model.DTO.CommentViewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentDAO commentDAO;

    public List<CommentDTO> commentList(String postId) {
        List<CommentDTO> commentList = new ArrayList<>();
        for (CommentDTO dto : commentDAO.commentList(Long.parseLong(postId))) {
            dto.setDate(ElapsedTimeCalculator.elapsedTime(dto.getDate()));
            commentList.add(dto);
        }
        return commentList;
    }

    public int commentListSize(String postId) {
        int size = 0;
        size = commentDAO.commentListSize(Long.parseLong(postId));
        return size;
    }

    public boolean registerComment(CommentDTO comment) {
        boolean result = false;
        result = commentDAO.registerComment(comment);
        return result;
    }

    public List<CommentViewDTO> commentViewList(String postId, String userId) {
        List<CommentViewDTO> commentViewList = new ArrayList<>();
        for (CommentViewDTO dto : commentDAO.commentViewList(Long.parseLong(postId))) {
            dto.setDate(ElapsedTimeCalculator.elapsedTime(dto.getDate()));
            dto.setUrl(dto.getUrl() != null ? dto.getUrl() : "/img/logo.png");
            commentViewList.add(dto);
        }
        return commentViewList;
    }
    public CommentViewDTO commentView(String userId, String postId) {
        CommentViewDTO comment = commentDAO.commentView(Long.parseLong(userId), Long.parseLong(postId));

        comment.setDate(ElapsedTimeCalculator.elapsedTime(comment.getDate()));
        comment.setUrl(comment.getUrl() != null ? comment.getUrl() : "/img/logo.png");

        return comment;
    }
}
