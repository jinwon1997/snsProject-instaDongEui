
package com.example.snsProject.model.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface ChatDAO{

    //List<ChatEntity> getAllfollowers(ChatDTO chatDTO) throws Exception;
    List<Map<String,Object>> getAllFollowers(long member_id);
    List<Map<String,Object>> getAllNotFollowers(long member_id); // 자기자신
    //List<String> getAllmessages();
    long selectRoom(long agent_id, long target_id);
    void insertRoom(long agent_id, long target_id);

    long getRoomId(long agent_id, long target_id);


}
