package com.example.myshop.msg;

/**
 * Created by 刘博良 on 2017/5/17.
 */

public class LoginRespMsg<T> extends BaseRespMsg {

    private String token;
    private T data;

    public void setToken(String token) {
        this.token = token;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public T getData() {
        return data;
    }
}
