package com.example.snsProject.controller;

import com.example.snsProject.model.DTO.PostAllDTO;
import com.example.snsProject.service.FollowService;
import com.example.snsProject.service.ProfileDetailService;
import com.example.snsProject.service.ProfileService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileDetailService profileDetailService;
    private List<PostAllDTO> postAllDTO;
    private final FollowService followService;
    private List<PostAllDTO> postAllBookmarkDTO;

    @GetMapping("/view/profile/follower") // 내가 팔로우 한 사람
    public String followers(@AuthenticationPrincipal UserDetails user, @RequestParam Map<String, Object> map, Model model){
        String followers_name = "";
        List<Map<String, Object>> result = null;
        result = profileService.getAllfollowers(followers_name,user.getUsername());
        //System.out.println("result : "+ result);
        model.addAttribute("followers",result);

        return "response/profile :: .follower_list";
    }


    @GetMapping("/view/profile/follow") // 나를 팔로우 한 사람 뜨기
    public String follow(@AuthenticationPrincipal UserDetails user, @RequestParam Map<String, Object> map, Model model){
        String followers_user = "";
        List<Map<String, Object>> result = null;

        result = profileService.getAllfollows(followers_user,user.getUsername());



        model.addAttribute("followers",result);

        return "response/profile :: .follower_list";
    }

    @GetMapping("/view/profile/followersearch")
    public String followersearch(@AuthenticationPrincipal UserDetails user, @RequestParam Map<String, Object> map, Model model){
        List<Map<String, Object>> result = null;
        String search_input = map.get("search_input").toString();
        result = profileService.getMyFollowers(search_input,user.getUsername());
        model.addAttribute("followers",result);

        return "response/profile :: .follower_list";
    }

    @GetMapping("/view/profile/followsearch")
    public String followsearch(@AuthenticationPrincipal UserDetails user, @RequestParam Map<String, Object> map, Model model){
        List<Map<String, Object>> result = null;
        String search_input = map.get("search_input").toString();
        result = profileService.getMyFollows(search_input,user.getUsername());
        //System.out.println("result : "+ result);
        model.addAttribute("followers",result);

        return "response/profile :: .follower_list";
    }

    @GetMapping("/view/profile/followbutton") // 팔로우 버튼 클릭
    public String followbutton(@AuthenticationPrincipal UserDetails user, @RequestParam Map<String, Object> map, Model model){
        String followers_user = "";
        List<Map<String, Object>> result = null;
        followers_user  = map.get("follow_member_id").toString();


        if (followService.followRelation(followers_user, user.getUsername())) { // 팔로우 버튼을 눌렀을 때
            followService.following(followers_user, user.getUsername());
        }else{                                                                  // 팔로우함 버튼을 눌렀을 때
            followService.followCancel(followers_user, user.getUsername());
        }



        result = profileService.getAllfollowers(followers_user,user.getUsername());
        model.addAttribute("followers",result);

        return "response/profile :: .follower_list"; // response/profile :: #follower_click    response/profile :: .follower_list
    }

    @GetMapping("/view/profile")
    public String Profile(@AuthenticationPrincipal UserDetails user, Model model) {
        postAllDTO = profileDetailService.getPosts(user.getUsername()); // 뒤에 숫자 2개는 안먹히게 해놨음.
        postAllBookmarkDTO = profileDetailService.getPostsBookmark(user.getUsername());

        List<Map<String,Object>> profileInfo = null;
        profileInfo = profileService.getProfileInfo(Long.parseLong(user.getUsername()));
        long CountPosts = profileService.CountPosts(user.getUsername()); //게시물 수
        long CountFollows = profileService.CountFollows(user.getUsername());; // 팔로우 수
        long CountFollowers = profileService.CountFollowers(user.getUsername());; // 팔로워 수

        model.addAttribute("CountPosts",CountPosts);
        model.addAttribute("CountFollows",CountFollows);
        model.addAttribute("CountFollowers",CountFollowers);
        model.addAttribute("profileInfo",profileInfo);

        model.addAttribute("postssizes", postAllDTO.size());
        model.addAttribute("posts", postAllDTO);


        model.addAttribute("postbookmarkssizes", postAllBookmarkDTO.size());
        model.addAttribute("postbookmarks", postAllBookmarkDTO);
        

        return "response/profile";
    }

}
