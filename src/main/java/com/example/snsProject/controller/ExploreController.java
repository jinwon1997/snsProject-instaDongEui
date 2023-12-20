package com.example.snsProject.controller;

import com.example.snsProject.model.DTO.PostAllDTO;
import com.example.snsProject.repository.Emoticon;
import com.example.snsProject.service.FollowService;
import com.example.snsProject.service.ImageService;
import com.example.snsProject.service.MemberService;
import com.example.snsProject.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/explore")
public class ExploreController {
    private final ImageService imageService;
    private final PageService pageService;
    private final MemberService memberService;
    private List<PostAllDTO> posts;
    private final FollowService followService;
    private final Emoticon emoticon;

//    @GetMapping("/")
//    public String responseLoadedGallery() {
//
//        return "";
//    }

    @PostMapping("/{postId}")
    public String responsePostView(@PathVariable(value="postId") int postId, Model model) {
        return "/post/postView";
    }

    @RequestMapping("")
    public String responseViewExplore() {
        return "explore";
    }

//    @ResponseBody
//    @PostMapping(value = "/")
//    public ResponseEntity<?> responseImgUrl() {
//        List<HashMap<?,?>> urlMap;
//        urlMap = imageService.getImagesUrlExplore();
//        System.out.println("touched Explore");
//        return ResponseEntity.ok(urlMap);
//    }

    @RequestMapping("/")
    public String responseExploreView(@AuthenticationPrincipal UserDetails user, Model model){
        posts = pageService.getExplorePosts(user.getUsername(),0,20);
        model.addAttribute("followRecommends",followService.recommendFollow(user.getUsername()));
        model.addAttribute("loginUser", memberService.findUser(user.getUsername()));
        model.addAttribute("emoticon", emoticon.getEmoticons());
        memberService.findUser(user.getUsername()).getUrl();
        model.addAttribute("posts", posts);
        return "response/explore_th";
    }

//    @RequestMapping(value = "/load", method = RequestMethod.POST)
//    public String responseLoadedContent(Model model) {
//        List<HashMap<?, ?>> urlMap;
//        urlMap = imageService.getPostListExplore();
//        model.addAttribute("result", urlMap);
//        return "/explore/LoadContent";
//    }
}
