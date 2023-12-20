package com.example.snsProject.controller;

import com.example.snsProject.model.DTO.UserDTO;
import com.example.snsProject.service.MemberService;
import com.example.snsProject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/registPost")
@RequiredArgsConstructor
public class RegistPostController {
    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/getPostUser")
    public ResponseEntity<Map<String, Object>> getPostUser(@AuthenticationPrincipal UserDetails user) {
        Map<String, Object> response = new HashMap<>();
        try {
            UserDTO userDTO = memberService.findUser(user.getUsername());
            response.put("userId", userDTO.getName());
            response.put("url", userDTO.getUrl());
            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> handleFileUpload(
            @AuthenticationPrincipal UserDetails user,
            @RequestPart("file") MultipartFile[] file,
            @RequestPart("text") String text){
        Map<String, Object> response = new HashMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        // 파일 및 텍스트 데이터 처리 로직 작성s
        if (!file[0].isEmpty()) {
            try {
                String[] file_name = new String[file.length];
                for (int i = 0; i < file.length; i++) {
                    file_name[i] = "upload_img_" + System.currentTimeMillis() + i + ".png";
                }
                // 저장할 경로 설정 (원하는 경로로 수정)
//                String uploadDir = "C:\\Users\\dita810\\Desktop\\snsProject (2)\\src\\main\\resources\\static\\img\\";
                String uploadDir = "C:\\Users\\dita810\\Desktop\\rollback (3)\\snsProject-6da1a664f0e2fde286afb0108d6a526d9897082a\\src\\main\\resources\\static\\img\\";

                if (postService.registerPost(user.getUsername(), text, file_name)) {
                    for(int i = 0; i < file.length; i++) {
                        File dest = new File(uploadDir + file_name[i]);
                        file[i].transferTo(dest);
                    }

                    response.put("success", true);
                }
            } catch (IOException e) {
                e.printStackTrace();
                response.put("success", false);
            }
        } else {
            response.put("success", false);
        }
        // 처리 결과에 따라 적절한 응답 반환
        return ResponseEntity.ok().headers(headers).body(response);
    }
}
