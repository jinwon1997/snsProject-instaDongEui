package com.example.snsProject.model.DAO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ImageDAO {
    List<Map<Integer, String>> getImagesUrl();
}
