package com.example.snsProject.model.DAO;

import com.example.snsProject.OAuth2.OAuth2Provider;

import java.util.Map;

public interface OAuth2UserInfo {

    OAuth2Provider getProvider();

    String getAccessToken();

    Map<String, Object> getAttributes();

    String getId();

    String getEmail();

    String getName();

    String getUserName();

    String getUrl();
}
