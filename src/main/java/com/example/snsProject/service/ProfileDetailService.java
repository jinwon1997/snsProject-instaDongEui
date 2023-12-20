package com.example.snsProject.service;

import com.example.snsProject.model.DAO.ProfileDAO;
import com.example.snsProject.model.DTO.PostAllDTO;
import com.example.snsProject.model.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileDetailService {
    private final FollowService followService;
    private final ProfileDAO profileDAO;
    private final CommentService commentService;
    private final BookmarkService bookmarkService;

    public List<PostAllDTO> getPosts(String userId) {
        List<PostViewDTO> posts = profileDAO.getPosts(userId);
        List<PostAllDTO> postAlls = new ArrayList<>();
        for (PostViewDTO post: posts) {
            PostAllDTO postAll = new PostAllDTO();
            postAll.setId(post.getId());
            postAll.setMemberId(post.getMemberId());
            postAll.setContent(post.getContent());
            postAll.setDate(ElapsedTimeCalculator.elapsedTime(post.getDate()));
            postAll.setUserName(post.getUserName());
            postAll.setName(post.getName());
            postAll.setEmail(post.getEmail());
            postAll.setUrl((post.getUrl() != null ? post.getUrl() : "/img/logo.png"));
            postAll.setImages(getPostImages(postAll.getId()));
            postAll.setLikes(getPostLikes(postAll.getId()));
            postAll.setTags(getPostTags(postAll.getId()));
            postAll.setCommentSize(commentService.commentListSize(post.getId().toString()));
            postAll.setLikeYN(profileDAO.likePost(post.getId(), Long.valueOf(userId)));
            postAll.setBookmarkYN(bookmarkService.bookmarkYN(userId, String.valueOf(post.getId())));
            postAll.setBookmarkYN(bookmarkService.bookmarkYN(userId, String.valueOf(post.getId())));
            postAlls.add(postAll);
        }
        return postAlls;
    }

    public List<PostAllDTO> getPostsBookmark(String userId) {

        List<PostViewDTO> postsBookmark = profileDAO.getPostsBookmark(userId);

        List<PostAllDTO> postAlls = new ArrayList<>();
        for (PostViewDTO post: postsBookmark) {
            PostAllDTO postAll = new PostAllDTO();
            postAll.setId(post.getId());
            postAll.setMemberId(post.getMemberId());
            postAll.setContent(post.getContent());
            postAll.setDate(ElapsedTimeCalculator.elapsedTime(post.getDate()));
            postAll.setUserName(post.getUserName());
            postAll.setName(post.getName());
            postAll.setEmail(post.getEmail());
            postAll.setUrl((post.getUrl() != null ? post.getUrl() : "/img/logo.png"));
            postAll.setImages(getPostImages(postAll.getId()));
            postAll.setLikes(getPostLikes(postAll.getId()));
            postAll.setTags(getPostTags(postAll.getId()));
            postAll.setCommentSize(commentService.commentListSize(post.getId().toString()));
            postAll.setLikeYN(profileDAO.likePost(post.getId(), Long.valueOf(userId)));
            postAll.setBookmarkYN(bookmarkService.bookmarkYN(userId, String.valueOf(post.getId())));
            postAlls.add(postAll);
        }

        return postAlls;
    }

    public List<PostImageDTO> getPostImages(Long postId) {
        List<PostImageDTO> postImages = profileDAO.getPostImages(postId);
        return postImages;
    }

    public List<PostLikeDTO> getPostLikes(Long postId) {
        List<PostLikeDTO> postLikes = profileDAO.getPostLikes(postId);
        return postLikes;
    }

    public List<PostTagDTO> getPostTags(Long postId) {
        List<PostTagDTO> postTags = profileDAO.getPostTags(postId);
        return postTags;
    }
}