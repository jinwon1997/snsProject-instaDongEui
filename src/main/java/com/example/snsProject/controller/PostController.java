package com.example.snsProject.controller;

import com.example.snsProject.service.PageService;
import com.example.snsProject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PageService pageService;

    @GetMapping("/registerLike")
    public ResponseEntity<Map<String, Object>> registerLike(@AuthenticationPrincipal UserDetails user, @RequestParam String postId) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (postService.registerLike(user.getUsername(), postId)) {
                response.put("count",pageService.getPostLikes(Long.valueOf(postId)).size());
                response.put("success", true);
                response.put("message", "좋아요 완료.");
            } else {
                response.put("success", false);
                response.put("message", "좋아요 실패.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cancelLike")
    public ResponseEntity<Map<String, Object>> cancelLike(@AuthenticationPrincipal UserDetails user, @RequestParam String postId) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (postService.cancelLike(user.getUsername(), postId)) {
                response.put("count",pageService.getPostLikes(Long.valueOf(postId)).size());
                response.put("success", true);
                response.put("message", "좋아요 취소 완료.");
            } else {
                response.put("success", false);
                response.put("message", "좋아요 취소 실패.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }


    /*@GetMapping("/{postId}")
    public String responsePostView(@PathVariable(value="postId") int postId, Model model) {
        List<HashMap<?, ?>> list = contentService.getPostContent(String.valueOf(postId));
        String html = postService.getPostHtml(list);
        model.addAttribute("postHtml",html);
        return "explore";
    }*/

    @GetMapping("/{postId}")
    public String responsePostView(@PathVariable(value="postId") int postId, Model model) {
        return "/post/postView";
    }

    @RequestMapping("")
    public String responseViewExplore() {
        return "explore";
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> responseImgUrl() {
        List<HashMap<?,?>> urlMap;
        urlMap = postService.getImagesUrl();
        return ResponseEntity.ok(urlMap);
    }

    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public String responseLoadedContent(Model model) {
        List<HashMap<?, ?>> urlMap;
        urlMap = postService.getImagesUrl();
        model.addAttribute("result", urlMap);
        return "/explore/LoadContent";
    }

    @GetMapping("/deletePost")
    public ResponseEntity<Map<String, Object>> deletePost(@RequestParam String postId, @AuthenticationPrincipal UserDetails user) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (postService.getPost(postId, user.getUsername())) {
                if (postService.deletePost(user.getUsername(), postId)) {
                    response.put("success", true);
                    response.put("message", "삭제 완료하였습니다.");
                } else {
                    response.put("success", false);
                    response.put("message", "삭제를 실패하였습니다.");
                }
            } else {
                response.put("success", false);
                response.put("message", "삭제 권한이 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/{postId}")
//    public String responsePostView(@PathVariable(value="postId") int postId, Model model) {
//        List<HashMap<Object,Object>> list = contentService.getPostContent(String.valueOf(postId));
//        model.addAttribute("date", list.get(0));
//        model.addAttribute("result", list);
//
//        return "/post/postView";
//    }
}
