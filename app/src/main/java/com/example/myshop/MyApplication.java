package com.example.myshop;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.myshop.Utils.UserLocalData;
import com.example.myshop.bean.User;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by 刘博良 on 2017/4/22.
 */

public class MyApplication extends Application {

    private User user;

    private static MyApplication myApplication;

    private Intent intent;

    public static MyApplication getMyApplication(){
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        myApplication =this;
        Fresco.initialize(this);
    }

    public void putIntent(Intent intent){
        this.intent =intent;
    }

    public Intent getIntent(){
        return intent;
    }

    public void jumpActivity(Context context){
        context.startActivity(intent);
        this.intent =null;
    }

    private void initUser(){
        this.user = UserLocalData.getUser(this);
    }

    public User getUser(){

        return user;
    }


    public void putUser(User user,String token){
        this.user = user;
        UserLocalData.putUser(this,user);
        UserLocalData.putToken(this,token);
    }

    public void clearUser(){
        this.user =null;
        UserLocalData.clearUser(this);
        UserLocalData.clearToken(this);


    }


    public String getToken(){

        return  UserLocalData.getToken(this);
    }


}
