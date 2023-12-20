package com.example.snsProject.model.DAO;

import com.example.snsProject.model.DTO.FollowRecommendDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowDAO {
    List<FollowRecommendDTO> recommendFollow(String userIds);
    int followRelation(Long followId, Long memberId);
    boolean following(Long followId, Long memberId);
    boolean followCancel(Long followId, Long memberId);
}