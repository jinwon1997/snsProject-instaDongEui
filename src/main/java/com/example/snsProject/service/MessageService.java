package com.example.snsProject.service;

import com.example.snsProject.model.DAO.MessageDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageDAO messageDAO;
    public List<Map<String,Object>> getAllMessage(long room_id){ // 메세지 뷰의 전체 요소 가져오기
        List<Map<String, Object>> getMessage = null;
        try {
            getMessage = messageDAO.getAllMessage(room_id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return getMessage;
    }
    public int insertMessage(long member_id, long room_id, long message_id, int type ,String content){
        int insertMessage_flag = -1;
        try {
            boolean flag = messageDAO.insertMessage(member_id,room_id,type);     //message 테이블 입력
            if (flag) {
                messageDAO.insertMessageText(message_id,content);// message_text테이블 입력(입력 종류에 따라 달라질 예정)
            }
            insertMessage_flag = 1;
        } catch (Exception e){
            insertMessage_flag = 0;
            e.printStackTrace();
        }
        return insertMessage_flag;
    }
    public long getMessageCount(){
        long getMessageCount_flag = -1;
        try {
            getMessageCount_flag = messageDAO.getMessageCount();
        } catch (Exception e){
            getMessageCount_flag = 0;
            e.printStackTrace();
        }
        return getMessageCount_flag;
    }
}
