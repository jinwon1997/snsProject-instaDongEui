package com.example.snsProject.service;

import com.example.snsProject.model.DAO.ShopDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ShopDAO shopDAO;

    public HashMap<String, String> getItemList() {
        return shopDAO.getItemList();
    }

    public void insertImpUid(long emoticonId, long memberId, String impUid) {
        shopDAO.insertImpUid(emoticonId, memberId, impUid);
    }
}
