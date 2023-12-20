package com.example.snsProject.model.DAO;

import com.example.snsProject.model.DTO.PostImageDTO;
import com.example.snsProject.model.DTO.PostLikeDTO;
import com.example.snsProject.model.DTO.PostTagDTO;
import com.example.snsProject.model.DTO.PostViewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface ProfileDAO {
    long CountPosts(long id);
    long CountFollowers(long id);
    long CountFollows(long id);

    List<Map<String,Object>> getProfileInfo(long member_id);

    List<PostViewDTO> getPostsBookmark(String userIds);

    List<PostViewDTO> getPosts(String userIds);
    List<PostImageDTO> getPostImages(Long postId);
    List<PostLikeDTO> getPostLikes(Long postId);
    List<PostTagDTO> getPostTags(Long postImageId);
    int likePost(Long postId, Long userId);
    boolean registerLike(Long postId, Long userId);
    boolean cancelLike(Long postId, Long userId);
    List<Map<String,Object>> getAllFollowers(long member_id);

    List<Map<String,Object>> getMyFollowers (String user_id, long member_id);

    List<Map<String,Object>> getAllFollows(long member_id);
    List<Map<String,Object>> getMyFollows (String user_id, long member_id);


}