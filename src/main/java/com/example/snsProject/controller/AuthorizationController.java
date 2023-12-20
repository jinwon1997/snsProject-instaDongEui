package com.example.snsProject.controller;


import com.example.snsProject.service.RegisterMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/method")
public class AuthorizationController {
    private final RegisterMemberService registerMemberService;


    //
    @PostMapping("/registUser")
    public  ResponseEntity<Map<String, Object>> registerUser(@RequestParam String phoneOrEmail,
                                                             @RequestParam String name,
                                                             @RequestParam String username,
                                                             @RequestParam String password) {
        
        Map<String, Object> response = new HashMap<>();
        try {
            if (registerMemberService.duplicateUser(phoneOrEmail) == 0) {
                registerMemberService.registerUser(username, password, name, phoneOrEmail);
                response.put("success", true);
                response.put("message", "등록 완료.");
            } else {
                response.put("success", false);
                response.put("message", "중복 데이터 확인.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }
}
