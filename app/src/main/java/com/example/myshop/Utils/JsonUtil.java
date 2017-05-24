package com.example.myshop.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;


/**
 * Created by 刘博良 on 2017/5/7.
 */

public class JsonUtil {

    private static Gson gson =new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static Gson getGson(){
        return gson;
    }

    public static <T> T fromJson(String json,Class<T> tClass){
        return gson.fromJson(json,tClass);
    }

    public static <T> T fromeJson(String json, Type type){
        return gson.fromJson(json,type);
    }

    public static String toJson(Object object){
        return gson.toJson(object);
    }
}
