package com.example.snsProject.controller;

import com.example.snsProject.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/explore")
@RequiredArgsConstructor
public class ImageController {
    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> responseImgUrl() {
        List<Map<Integer,String>> urlList = null;
        urlList = imageService.getImagesUrl();
        return ResponseEntity.ok(urlList);
    }

}
