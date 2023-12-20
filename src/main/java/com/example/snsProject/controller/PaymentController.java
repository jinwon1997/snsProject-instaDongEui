package com.example.snsProject.controller;

import com.example.snsProject.model.DTO.UserDTO;
import com.example.snsProject.service.MemberService;
import com.example.snsProject.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final MemberService memberService;
    private final PaymentService paymentService;

//    @PostMapping("/complete")
//    public ResponseEntity<?> responseCompletePayments() {
//
//    }
    @ResponseBody
    @PostMapping("/getUserInfo")
    public ResponseEntity<?> responseUserInfo(@AuthenticationPrincipal UserDetails user) {
        UserDTO dto =  memberService.findUser(user.getUsername());
        HashMap<String,String> userInfo = new HashMap<>();
        try {
            userInfo.put("buyer_email", dto.getEmail());
            userInfo.put("buyer_name", dto.getName());
            userInfo.put("buyer_tel", dto.getPhone());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(userInfo);
    }
    @RequestMapping("/")
    public String requestShopHtml(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("already", memberService.findUser(user.getUsername()));
        return "shopmain";
    }

    @ResponseBody
    @PostMapping("/paymentProc")
    public ResponseEntity<?> requestItem(@AuthenticationPrincipal UserDetails user,
                              @RequestParam String impUid, @RequestParam String emoticonId) {
        boolean flag = false;
        System.out.println("유저 아이디: "+ user.getUsername() + "아이템 아이디: " + emoticonId + "구매고유번호: "+ impUid);
        try {
            paymentService.insertImpUid(Long.parseLong(emoticonId), Long.parseLong(user.getUsername()),impUid);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(flag);
    }
}
