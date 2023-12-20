package com.example.snsProject.model.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface Profile_chDAO {
    List<Map<String,Object>> getAllUserInfo(String member_id);

    void updateUserInfo(String url,String introduce,int gender,String member_id);
}
