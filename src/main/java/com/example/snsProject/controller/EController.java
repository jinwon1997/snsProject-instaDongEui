package com.example.snsProject.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller; // 이 부분을 추가하세요
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EController implements ErrorController {

    @GetMapping("/errors")
    public String handleError() {
        return "response/error";
    }

    public String getErrorPath() {
        return "/errors";
    }
}