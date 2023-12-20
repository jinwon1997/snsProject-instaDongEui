package com.example.snsProject.service;


import com.example.snsProject.model.DAO.BookmarkDAO;
import com.example.snsProject.model.DTO.BookmarkDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkDAO bookmarkDAO;

    public boolean registerBookmark(BookmarkDTO bookmark) {
        return bookmarkDAO.registerBookmark(bookmark);
    }

    public boolean deleteBookmark(String memberId,  String postId) {
        return bookmarkDAO.deleteBookmark(Long.parseLong(memberId), Long.parseLong(postId));
    }

    public int bookmarkYN(String memberId,  String postId) {
        return (bookmarkDAO.bookmarkYN(Long.parseLong(memberId), Long.parseLong(postId)));
    }

    public int countBookmark(String postId) {
        int size = 0;
        size = bookmarkDAO.countBookmark(Long.parseLong(postId));
        return size;
    }
}
