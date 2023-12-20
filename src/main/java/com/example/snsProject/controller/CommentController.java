package com.example.snsProject.controller;

import com.example.snsProject.model.DTO.CommentDTO;
import com.example.snsProject.model.DTO.CommentViewDTO;
import com.example.snsProject.service.CommentService;
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
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/registerComment")
    public ResponseEntity<Map<String, Object>> registerComment(@AuthenticationPrincipal UserDetails user, @RequestParam String comment, @RequestParam String postId) {
        Map<String, Object> response = new HashMap<>();
        CommentDTO commentValue = new CommentDTO(Long.parseLong(user.getUsername()),Long.parseLong(postId), comment);
        try {
            if (commentService.registerComment(commentValue)) {
                CommentViewDTO dto = commentService.commentView(user.getUsername(), postId);
                response.put("success", true);
                response.put("count", commentService.commentListSize(postId));
                response.put("message", "댓글 등록 성공.");
                response.put("append", dto);
            } else {
                response.put("success", false);
                response.put("message", "댓글 등록 실패.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }
}
