package com.candychat.utils;

/**
 * Created by admin on 2016/11/22.
 */

public class FilterWordUtils {

    public static String filterWord(String string) {
        String result = null;
        //关键词替换
//        result = string.replaceAll("(?i)whatsapp", "********")
//                .replaceAll("(?i)Snapchat", "********")
//                .replaceAll("\\d{5,}", "********");
        //整句替换
        result = string.replaceAll(".*(?i)whatsapp.*", "********")
                .replaceAll(".*(?i)Snapchat.*", "********")
                .replaceAll(".*\\d{5,}.*", "********");

        return result;
    }
}
