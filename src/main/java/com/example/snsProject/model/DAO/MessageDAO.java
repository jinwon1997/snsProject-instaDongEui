package com.example.snsProject.model.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface MessageDAO {
    List<Map<String,Object>> getAllMessage(long room_id);
    boolean insertMessage(long member_id, long room_id, int type);
    void insertMessageText(long message_id, String content);
    long getMessageCount();
}
