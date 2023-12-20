package com.example.snsProject.model.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EmoticonModuleDTO {
    long id;
    long emoticonId;
    String name;
    String url;
    String uuid;
}
