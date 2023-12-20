package com.example.snsProject.controller;

import com.example.snsProject.service.FollowService;
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
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @GetMapping("/followUser")
    public ResponseEntity<Map<String, Object>> followUser(@AuthenticationPrincipal UserDetails user, @RequestParam String followId) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (followService.followRelation(followId, user.getUsername())) {
                if (followService.following(followId, user.getUsername())) {
                    response.put("success", true);
                    response.put("message", "팔로우 완료.");
                } else {
                    response.put("success", false);
                    response.put("message", "팔로우 실패.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/followCancel")
    public ResponseEntity<Map<String, Object>> followCancel(@AuthenticationPrincipal UserDetails user, @RequestParam String followId) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (!followService.followRelation(followId, user.getUsername())) {
                if (followService.followCancel(followId, user.getUsername())) {
                    response.put("success", true);
                    response.put("message", "팔로우 취소 완료.");
                } else {
                    response.put("success", false);
                    response.put("message", "팔로우 취소 실패.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }
}
