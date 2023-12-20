package com.example.snsProject.service;

import com.example.snsProject.model.DAO.EmoticonDAO;
import com.example.snsProject.model.DTO.EmoticonDTO;
import com.example.snsProject.model.DTO.EmoticonModuleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmoticonService {
    private final EmoticonDAO emoticonDAO;


    public List<EmoticonModuleDTO> selectEmoticonModule(String emoticonIds) {
        List<EmoticonModuleDTO> emoticonDtos = new ArrayList<>();
        if (emoticonIds != null ) {
            if (emoticonIds.split(";").length > 0) {
                emoticonDtos.addAll(emoticonDAO.selectEmoticonModule(emoticonIds.replace(";", ",").substring(0, emoticonIds.length() - 1)));
            }
        };
        return emoticonDtos;
    }
}
