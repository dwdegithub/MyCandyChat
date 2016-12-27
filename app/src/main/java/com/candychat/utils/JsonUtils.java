package com.candychat.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Created by zuo on 2016/8/26.
 */
public class JsonUtils {

    private static Gson mgson = new Gson();

    public static <T> T parseJsonToBean(String json, Class<T> tClass) throws JsonSyntaxException{
        return mgson.fromJson(json, tClass);
    }

    public static String parseBeanToJson(Object obj){
        return mgson.toJson(obj);
    }
}
