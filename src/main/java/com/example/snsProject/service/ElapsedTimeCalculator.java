package com.example.snsProject.service;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ElapsedTimeCalculator {

    public static String elapsedTime(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date start = dateFormat.parse(dateString);
            Date end = new Date();

            long diffInSeconds = (end.getTime() - start.getTime()) / 1000;

            long[] times = {
                    60 * 60 * 24 * 365, // 년
                    60 * 60 * 24 * 30,  // 개월
                    60 * 60 * 24,       // 일
                    60 * 60,            // 시간
                    60                  // 분
            };

            for (int i = 0; i < times.length; i++) {
                long betweenTime = diffInSeconds / times[i];

                if (betweenTime > 0) {
                    return betweenTime + getUnitName(i) + " 전";
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "방금 전";
    }

    private static String getUnitName(int index) {
        String[] unitNames = {"년", "개월", "일", "시간", "분"};
        return unitNames[index];
    }

}
