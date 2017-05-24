package com.example.myshop.Http;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 刘博良 on 2017/4/18.
 */

public abstract class BaseCallBack<T> {
    public Type type;

    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public BaseCallBack() {
        this.type = getSuperclassTypeParameter(getClass());
    }

    public abstract void onBeforeRequest(Request request);

    public abstract void onFailure(IOException e);

    public abstract void onResponse(Response response) ;

    public abstract void onSucessful(Request request,T t);

    public abstract void onError(Request request,int code,Exception e);

    public abstract void onTokenError(Request request,int code);
}
