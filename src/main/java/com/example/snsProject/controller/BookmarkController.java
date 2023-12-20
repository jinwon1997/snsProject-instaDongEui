package com.example.snsProject.controller;

import com.example.snsProject.model.DTO.BookmarkDTO;
import com.example.snsProject.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @GetMapping("/registerBookmark")
    public ResponseEntity<Map<String, Object>> registerBookmark(@AuthenticationPrincipal UserDetails user, @RequestParam String postId) {
        Map<String, Object> response = new HashMap<>();
        BookmarkDTO bookmarkDTO = new BookmarkDTO(0, Long.parseLong(user.getUsername()),Long.parseLong(postId));
        try {
            if (bookmarkService.bookmarkYN(user.getUsername(), postId)  == 0 && bookmarkService.registerBookmark(bookmarkDTO)) {
                response.put("success", true);
                response.put("message", "북마크 등록 성공.");
            } else if(bookmarkService.bookmarkYN(user.getUsername(), postId)  > 0 && bookmarkService.deleteBookmark(user.getUsername(), postId)){
                response.put("success", true);
                response.put("message", "북마크 삭제 완료.");
            }

            else {
                response.put("success", false);
                response.put("message", "북마크 등록 실패.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }
}
