package com.example.snsProject.service;


import com.example.snsProject.model.DAO.ProfileDAO;
import com.example.snsProject.model.DTO.PostImageDTO;
import com.example.snsProject.model.DTO.PostLikeDTO;
import com.example.snsProject.model.DTO.PostTagDTO;
import com.example.snsProject.model.DTO.PostViewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileDAO profileDAO;
    private final FollowService followService;

    public long CountPosts(String id){

        return profileDAO.CountPosts(Long.parseLong(id)) ;
    }
    public long CountFollows(String id){
        return profileDAO.CountFollows(Long.parseLong(id)) ;

    }
    public long CountFollowers(String id){

        return profileDAO.CountFollowers(Long.parseLong(id)) ;
    }

    public  List<Map<String,Object>> getProfileInfo(long member_id){
        List<Map<String,Object>> getProfileImg_result = null;
        try {
            getProfileImg_result = profileDAO.getProfileInfo(member_id);
            if(getProfileImg_result.get(0).get("url") == null)
                getProfileImg_result.get(0).put("url","");
            if(getProfileImg_result.get(0).get("introduce") == null)
                getProfileImg_result.get(0).put("introduce","");
        }catch (Exception e){
            e.printStackTrace();
        }
        return getProfileImg_result;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<PostViewDTO> getPostsBookmark(String userIds){
        List<PostViewDTO> result= null;
        try {
            result = profileDAO.getPostsBookmark(userIds);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public List<PostViewDTO> getPosts(String userIds){       // 자신의 게시물을 몇개까지 가져올 건지
        List<PostViewDTO> result= null;
        try {
            result = profileDAO.getPosts(userIds);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    List<PostImageDTO> getPostImages(Long postId){                            // postimage table
        List<PostImageDTO> result= null;
        try {
            result = profileDAO.getPostImages(postId);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    List<PostLikeDTO> getPostLikes(Long postId){                             // postlike table
        List<PostLikeDTO> result= null;
        try {
            result = profileDAO.getPostLikes(postId);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    List<PostTagDTO> getPostTags(Long postImageId){                         // posttag table
        List<PostTagDTO> result= null;
        try {
            result = profileDAO.getPostTags(postImageId);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    int likePost(Long postId, Long userId){                                 // 좋아요 수 가져오기
        int result = 0;
        try {
            result = profileDAO.likePost(postId, userId);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    boolean registerLike(Long postId, Long userId){                        // 자신의 좋아요 on
        boolean result = false;
        try {
            result = profileDAO.registerLike(postId, userId);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    boolean cancelLike(Long postId, Long userId){                           // 자신의 좋아요 off
        boolean result = false;
        try {
            result = profileDAO.cancelLike(postId, userId);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public List<Map<String,Object>> getAllfollowers(String followers_user,String member_id){
        List<Map<String, Object>> result = null;

        try {
            result = profileDAO.getAllFollowers(Long.parseLong(member_id));

            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).get("url") == null) {
                    result.get(i).put("url", "");
                }
                if (followService.followRelation(result.get(i).get("id").toString(), member_id)) { // 나를 팔로우한 상대를 내가 팔로우 할 수 있을 때
                    result.get(i).put("follow_check", "true");
                } else {
                    result.get(i).put("follow_check", "");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("getAllfollows 에러!!!!");
        }
        return result;
    }

    public List<Map<String,Object>> getMyFollowers(String search_input,String member_id){
        List<Map<String, Object>> result = null;
        try {
            result = profileDAO.getMyFollowers(search_input,Long.parseLong(member_id));
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).get("url") == null) {
                    result.get(i).put("url", "");
                }
                if (followService.followRelation(result.get(i).get("id").toString(), member_id)) { // 나를 팔로우한 상대를 내가 팔로우 할 수 있을 때
                    result.get(i).put("follow_check", "true");
                } else {
                    result.get(i).put("follow_check", "");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("getmyfollowers 에러!!!!");
        }
        return result;
    }

    public List<Map<String,Object>> getAllfollows(String followers_user,String member_id){
        List<Map<String, Object>> result = null;
        try {
            result = profileDAO.getAllFollows(Long.parseLong(member_id)); // 나를 팔로우한 사람

            for(int i = 0; i < result.size(); i++) {
                if (result.get(i).get("url") == null) {
                    result.get(i).put("url", "");
                }
                result.get(i).put("follow_check","false");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("getAllfollows 에러!!!!");
        }
        return result;
    }

    public List<Map<String,Object>> getMyFollows(String search_input,String member_id){
        List<Map<String, Object>> result = null;
        try {
            result = profileDAO.getMyFollows(search_input,Long.parseLong(member_id));
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).get("url") == null) {
                    result.get(i).put("url", "");
                }
                result.get(i).put("follow_check","false");
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("getmyfollowers 에러!!!!");
        }
        return result;
    }
}