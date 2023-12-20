package com.example.snsProject.service;

import com.example.snsProject.model.DAO.ImageDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {
    ImageDAO imageDAO;

    public List<Map<Integer,String>> getImagesUrl(){
        return imageDAO.getImagesUrl();
    }
}
