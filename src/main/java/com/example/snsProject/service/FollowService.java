package com.example.snsProject.service;

import com.example.snsProject.model.DAO.FollowDAO;
import com.example.snsProject.model.DAO.UserDAO;
import com.example.snsProject.model.DTO.FollowDTO;
import com.example.snsProject.model.DTO.FollowRecommendDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserDAO userDAO;
    private final FollowDAO followDAO ;

    public List<FollowRecommendDTO> recommendFollow(String userId) {
        String userIds = followList(userId);
        List<FollowRecommendDTO> followRecommendList = new ArrayList<>();
        for (FollowRecommendDTO follow: followDAO.recommendFollow(userIds)) {
            if (follow.getUrl()== null || follow.getUrl().isBlank()) {
                follow.setUrl("/img/logo.png");
            }

            followRecommendList.add(follow);
        }
        return followRecommendList;
    }

    public String followList(String userId) {
        List<FollowDTO> list = userDAO.findFollowUsers(Long.parseLong(userId));
        String followList = userId;
        if (list != null && !list.isEmpty()) {
            followList += "," + list.stream()
                    .map(FollowDTO::getFollowId)
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
        }
        return followList;
    }

    public boolean followRelation(String followId, String memberId) {
        boolean result = false;
        int i = followDAO.followRelation(Long.parseLong(followId), Long.parseLong(memberId));
        if (i == 0) {
            result = true;
        }
        return result;
    }
    public boolean following(String followId, String memberId) {
        return followDAO.following(Long.parseLong(followId), Long.parseLong(memberId));
    }

    public boolean followCancel(String followId, String memberId) {
        return followDAO.followCancel(Long.parseLong(followId), Long.parseLong(memberId));
    }
}
