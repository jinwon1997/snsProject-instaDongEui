package com.example.snsProject.model.DAO;

import com.example.snsProject.model.DTO.CommentDTO;
import com.example.snsProject.model.DTO.CommentViewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDAO {
    boolean registerComment(CommentDTO comment);
    List<CommentDTO> commentList(long postId);
    int commentListSize(long postId);
    List<CommentViewDTO> commentViewList(long postId);

    CommentViewDTO commentView(long userId, long postId);
}
