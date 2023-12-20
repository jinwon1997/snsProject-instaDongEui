package com.example.snsProject.repository;

import com.example.snsProject.model.DAO.EmoticonDAO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

@Service
@RequiredArgsConstructor
public class Emoticon {

    private static volatile Emoticon repository;
    private final EmoticonDAO emoticonDAO;

    @Getter
    private final String[] emoticons = new String[] {
    "\uD83D\uDE02", "\uD83D\uDE05", "\uD83E\uDD23", "\uD83D\uDE25", "\uD83D\uDE06", "\uD83D\uDE0D", "\uD83D\uDE31",
            "\uD83D\uDE34", "\uD83D\uDE35", "\uD83D\uDE37", "\uD83E\uDD25", "\uD83D\uDE1D", "\uD83E\uDD2B", "\uD83E\uDD2D",
            "⌚","⏰","⏱", "⚽", "⚾", "\uD83C\uDFD0", "\uD83C\uDFC0", "\uD83C\uDFC8", "\uD83C\uDFD3", "☝", "✌", "✋",
            "\uD83D\uDC46", "\uD83D\uDC47", "\uD83D\uDC48", "\uD83D\uDC49", "\uD83D\uDC4D", "\uD83D\uDC4E", "\uD83D\uDC4C",
            "\uD83E\uDD1F", "\uD83C\uDF2D", "\uD83C\uDF54", "\uD83C\uDF2E", "\uD83C\uDF55", "\uD83C\uDF5E", "\uD83C\uDF69",
            "\uD83E\uDD5E", "\uD83E\uDD63", "☕", "\uD83E\uDD5B", "\uD83C\uDF37", "\uD83C\uDF3B", "\uD83C\uDF3C", "\uD83C\uDF3D",
            "\uD83C\uDF45", "\uD83C\uDF46", "\uD83C\uDF47", "\uD83C\uDF49", "\uD83C\uDF4A", "\uD83C\uDF4D", "\uD83C\uDF4E",
            "\uD83C\uDF52", "\uD83C\uDF53", "\uD83E\uDD5D", "\uD83C\uDF02", "\uD83C\uDF89", "\uD83C\uDFA8", "\uD83D\uDC8A",
            "📗", "📘","📕","\uD83D\uDD0A","\uD83C\uDFB8"," \uD83C\uDFB9","\uD83C\uDFBA","\uD83C\uDFBB","\uD83D\uDC24","\uD83E\uDD85",
        "\uD83E\uDD89","\uD83D\uDC35","\uD83D\uDC36","\uD83D\uDC39","\uD83E\uDD81","\uD83D\uDC40","\uD83D\uDC42","\uD83D\uDC43",
        "\uD83D\uDE4F","\uD83D\uDE45","\uD83D\uDE46","\uD83E\uDD26","\uD83D\uDC44","\uD83D\uDC8B","\uD83D\uDC45","\uD83E\uDDE1",
        "\uD83D\uDC96","\uD83D\uDC98","\uD83D\uDC95","\uD83D\uDC8F","\uD83D\uDC91","\uD83D\uDE81","\uD83D\uDE8A","\uD83D\uDE91",
        "\uD83D\uDE95","\uD83D\uDE9A","\uD83D\uDE9B"
    };

    public void emoticonAdd(String emoticon) {
        emoticonDAO.emoticonAdd(emoticon);
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Emoticon.class);

        Emoticon emoticon = context.getBean(Emoticon.class);
        for (int i = 3; i < Emoticon.repository.getEmoticons().length; i++) {
            emoticon.emoticonAdd(Emoticon.repository.getEmoticons()[i]);
        }
    }
}
