package com.example.snsProject.controller;

import com.example.snsProject.service.Profile_chService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class Profile_chController {
    private final Profile_chService profile_chService;

    @RequestMapping("/view/profile_ch")
    public String profile_ch1(@AuthenticationPrincipal UserDetails user,
                              Model model){
        List<Map<String, Object>> result = null;

        try {
            result = profile_chService.getAllUserInfo(user.getUsername());
            model.addAttribute("result",result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "response/profile_change";
    }

    @PostMapping("/view/profile_ch2")
    public ResponseEntity<Map<String, Object>> profile_ch2(
            @AuthenticationPrincipal UserDetails user,
            @RequestParam("file") MultipartFile file,
            @RequestParam("introduce") String introduce,
            @RequestParam("gender") int gender) {

        Map<String, Object> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("success", false);
            response.put("message", "파일이 비어있습니다.");
            return ResponseEntity.ok(response);
        }

        try {
            String uploadDir = "C:\\Users\\dita810\\Desktop\\rollback (3)\\snsProject-6da1a664f0e2fde286afb0108d6a526d9897082a\\src\\main\\resources\\static\\img\\";

            String originalFileName = file.getOriginalFilename();
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String formattedDateTime = currentDateTime.format(formatter);
            File dest = new File(uploadDir + formattedDateTime +file.getOriginalFilename());
            file.transferTo(dest);

            profile_chService.updateUserInfo("/img/" + dest.getName(), introduce, gender, user.getUsername());

            response.put("success", true);
            response.put("message", "파일 업로드 성공!");
        } catch (IOException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "파일 업로드 실패: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }
}
