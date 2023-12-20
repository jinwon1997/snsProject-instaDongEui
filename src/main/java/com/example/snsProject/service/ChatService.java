package com.example.snsProject.service;

import com.example.snsProject.model.DAO.ChatDAO;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatDAO chatDAO;
    public List<Map<String,Object>> getAllfollowers(long member_id){
        List<Map<String, Object>> result = null;
        try {
            result = chatDAO.getAllFollowers(member_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public List<Map<String,Object>> getAllNotfollowers(long member_id){
        List<Map<String, Object>> result = null;
        try {
            result = chatDAO.getAllNotFollowers(member_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public long selectRoom(long agent_id, long target_id){
        long selectRoom_result = -1;
        try {
            selectRoom_result = chatDAO.selectRoom(agent_id, target_id);// 만들어진 채팅방이 존재하는지 확인
        } catch (Exception e) {
            selectRoom_result = 0;
            e.printStackTrace();
        }

        return selectRoom_result;
    }
    public long insertRoom(long agent_id, long target_id){
        long insertRoom_result = -1;
        try {
            chatDAO.insertRoom(agent_id,target_id);
            insertRoom_result = 1;
        } catch (Exception e){
            insertRoom_result = 0;
            e.printStackTrace();
        }

        return insertRoom_result;
    }

    public long getRoomId(long agent_id, long target_id){
        long getRoomId = -1;
        try {
            getRoomId = chatDAO.getRoomId(agent_id,target_id);
        } catch (Exception e){
            getRoomId = 0;
            e.printStackTrace();
        }
        return getRoomId;
    }


}


