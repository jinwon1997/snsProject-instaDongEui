package com.example.snsProject.controller;

import com.example.snsProject.model.DTO.MemberLoginDTO;
import com.example.snsProject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/method")
public class LoginController {
    private final MemberService memberService;

    @PostMapping("/loginProc")
    public String login(MemberLoginDTO dto) {
        boolean isValidMember = memberService.isValidMember(dto);
        String result_url = "/loginTest";
        if (isValidMember)
            result_url = "/view/home";
        return result_url;
    }

}

