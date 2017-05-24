package com.example.myshop.Utils;

import android.content.Context;

import com.example.myshop.Http.UrlContent;
import com.example.myshop.bean.User;

/**
 * Created by 刘博良 on 2017/5/18.
 */

public class UserLocalData  {

    public static void putUser (Context context, User user){

        String user_json =JsonUtil.toJson(user);
        PerferenceUtils.putString(context, UrlContent.USER_JSON,user_json);
    }

    public static void putToken(Context context,String token){
        PerferenceUtils.putString(context,UrlContent.TOKEN,token);
    }

    public static User getUser(Context context){
        String user_json =PerferenceUtils.getString(context,UrlContent.USER_JSON);
        if (user_json!= ""&&user_json!=null)
            return JsonUtil.fromeJson(user_json,User.class);
        return null;
    }

    public static String getToken(Context context){
        return PerferenceUtils.getString(context,UrlContent.TOKEN);
    }

    public static void clearUser(Context context){
        PerferenceUtils.putString(context,UrlContent.USER_JSON,"");
    }

    public static void clearToken(Context context){
        PerferenceUtils.putString(context,UrlContent.TOKEN,"");
    }



}
