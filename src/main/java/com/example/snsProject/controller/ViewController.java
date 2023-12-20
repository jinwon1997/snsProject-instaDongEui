package com.example.snsProject.controller;

import com.example.snsProject.model.DTO.PostAllDTO;
import com.example.snsProject.repository.Emoticon;
import com.example.snsProject.service.EmoticonService;
import com.example.snsProject.service.FollowService;
import com.example.snsProject.service.MemberService;
import com.example.snsProject.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/view")
@RequiredArgsConstructor
public class ViewController {
    private final PageService pageService;
    private final MemberService memberService;
    private List<PostAllDTO> posts;
    private final FollowService followService;
    private final EmoticonService emoticonService;
    private final Emoticon emoticon;

    @RequestMapping("/loginTest")
    public String login() throws Exception {
        return "response/login";
    }

    @RequestMapping("/register")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!!");
        return "response/register";
    }

    @RequestMapping("/home")
    public String responseHome(@AuthenticationPrincipal UserDetails user, Model model) {
        posts = pageService.getPosts(user.getUsername(),0,8);
        model.addAttribute("followRecommends",followService.recommendFollow(user.getUsername()));
        model.addAttribute("loginUser", memberService.findUser(user.getUsername()));
//        model.addAttribute("emoticon", emoticonService.selectEmoticonModule(memberService.findUser(user.getUsername()).getEmoticon()));
        model.addAttribute("emoticon", emoticon.getEmoticons());
        memberService.findUser(user.getUsername()).getUrl();
        model.addAttribute("posts", posts);
        String result_url = "/view/loginTest";
        if (user.getUsername() != null) {
            result_url = "response/home";
        }
        return result_url;
    }

    @RequestMapping("/navbar")
    public String responseNavbar() {
        return "response/navbar";
    }

    @RequestMapping("/posts")
    public String responsePost(@AuthenticationPrincipal UserDetails user, Model model) {
        return "response/posts";
    }
    @RequestMapping("/registPost")
    public String registPost(@AuthenticationPrincipal UserDetails user) {
        return "response/registPost";
    }


}
