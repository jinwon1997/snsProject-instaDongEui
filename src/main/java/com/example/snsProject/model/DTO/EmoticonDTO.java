package com.example.snsProject.model.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class EmoticonDTO {
    long id;
    String name;
    String explanation;
    int price;
    List<EmoticonModuleDTO> module;
}
