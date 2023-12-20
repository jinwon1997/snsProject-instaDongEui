package com.example.snsProject.model.DAO;

import com.example.snsProject.model.DTO.BookmarkDTO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface BookmarkDAO {
    boolean registerBookmark(BookmarkDTO bookmark);
    boolean deleteBookmark(long memberId, long postId);
    int bookmarkYN(long memberId, long postId);
    int countBookmark(Long postId);
}