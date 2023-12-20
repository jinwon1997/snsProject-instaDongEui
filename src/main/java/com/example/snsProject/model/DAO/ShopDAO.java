package com.example.snsProject.model.DAO;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface ShopDAO {
    HashMap<String, String> getItemList();
    void insertImpUid(long emoticonId, long memberId, String impUid);
}
