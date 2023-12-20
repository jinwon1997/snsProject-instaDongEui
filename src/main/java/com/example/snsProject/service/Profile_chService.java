package com.example.snsProject.service;

import com.example.snsProject.model.DAO.ProfileDAO;
import com.example.snsProject.model.DAO.Profile_chDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Profile_chService {
    private final Profile_chDAO profile_chDAO;

    public List<Map<String,Object>> getAllUserInfo(String member_id){
        List<Map<String, Object>> result = null;
        try {
            result = profile_chDAO.getAllUserInfo(member_id);
            if(result.get(0).get("url") == null)
                result.get(0).put("url","");
            if(result.get(0).get("introduce") == null)
                result.get(0).put("introduce","");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private final String uploadPath = "/img";

    public void updateUserInfo(String url,String introduce,int gender,String member_id){
        try {
            profile_chDAO.updateUserInfo(url,introduce,gender,member_id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
