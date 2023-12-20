package com.example.snsProject.model.DAO;

import com.example.snsProject.model.DTO.EmoticonModuleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmoticonDAO {
    List<EmoticonModuleDTO> selectEmoticonModule(String emoticonIds);
    void emoticonAdd(String emoticon);
}
