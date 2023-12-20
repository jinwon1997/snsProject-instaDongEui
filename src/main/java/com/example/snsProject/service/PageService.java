package com.example.snsProject.service;

import com.example.snsProject.model.DAO.PostDAO;
import com.example.snsProject.model.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PageService {
    private final FollowService followService;
    private final PostDAO postDAO;
    private final CommentService commentService;
    private final BookmarkService bookmarkService;

    public List<PostAllDTO> getPosts(String userId, int start, int cnt) {
        String userIds = followService.followList(userId);
        List<PostViewDTO> posts = postDAO.getPosts(userIds, start, cnt);
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
            postAll.setComments(commentService.commentViewList(String.valueOf(post.getId()),userId));
            postAll.setCommentSize(commentService.commentListSize(post.getId().toString()));
            postAll.setLikeYN(postDAO.likePost(post.getId(), Long.valueOf(userId)));
            postAll.setBookmarkYN(bookmarkService.bookmarkYN(userId, String.valueOf(post.getId())));
            postAlls.add(postAll);
        }
        return postAlls;
    }

    public List<PostAllDTO> getExplorePosts(String userId, int start, int cnt) {
        List<PostViewDTO> posts = postDAO.getExplorePosts(start, cnt);
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
            postAll.setComments(commentService.commentViewList(String.valueOf(post.getId()),userId));
            postAll.setCommentSize(commentService.commentListSize(post.getId().toString()));
            postAll.setLikeYN(postDAO.likePost(post.getId(), Long.valueOf(userId)));
            postAll.setBookmarkYN(bookmarkService.bookmarkYN(userId, String.valueOf(post.getId())));
            postAlls.add(postAll);
        }
        return postAlls;
    }

    public List<PostImageDTO> getPostImages(Long postId) {
        List<PostImageDTO> postImages = postDAO.getPostImages(postId);
        return postImages;
    }

    public List<PostLikeDTO> getPostLikes(Long postId) {
        List<PostLikeDTO> postLikes = postDAO.getPostLikes(postId);
        return postLikes;
    }

    public List<PostTagDTO> getPostTags(Long postId) {
        List<PostTagDTO> postTags = postDAO.getPostTags(postId);
        return postTags;
    }
}
