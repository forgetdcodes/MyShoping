package com.example.myshop.Http;



import android.os.Handler;
import android.os.Looper;

import com.example.myshop.MyApplication;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 刘博良 on 2017/4/18.
 */

public class OkHttpHelper {

    private static  OkHttpClient okHttpClient;
    private Gson mGson;
    private Handler handler;

    public static final int TOKEN_MISSING=401;// token 丢失
    public static final int TOKEN_ERROR=402; // token 错误
    public static final int TOKEN_EXPIRE=403; // token 过期

    private OkHttpHelper(){
        okHttpClient = new OkHttpClient();
        mGson =new Gson();
        handler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpHelper getOkHttpHelper(){

        OkHttpHelper okHttpHelper =new OkHttpHelper();
        return okHttpHelper;
    }

    public void get(String url,Map<String,String> params,BaseCallBack callBack){

        Request request =buildRequest(url,params,HttpMethodType.GET);
        doRequest(request,callBack);
    }

    public void get(String url,BaseCallBack callBack){
        get(url,null,callBack);
    }

    public void post(String url, Map<String,String> params,
                     BaseCallBack callBack){

        Request request =buildRequest(url,params,HttpMethodType.POST);
        doRequest(request,callBack);

    }

    public void doRequest(final Request request, final BaseCallBack callBack){

        callBack.onBeforeRequest(request);

        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()){
                    callBack.onResponse(response);
                    String result = response.body().string();
                    if (callBack.type == String.class){
                        onSuccessful(callBack,request,String.class);
                    }
                    else{
                        try {
                            Object object =mGson.fromJson(result,callBack.type);
                            onSuccessful(callBack,request,object);
                        }catch (JsonParseException e){
                            callBack.onError(request,10086,e);
                        }
                    }
                }
                else if(response.code()==TOKEN_ERROR||response.code()==TOKEN_EXPIRE||
                        response.code()==TOKEN_MISSING){
                    onTokenError(callBack,request);
                }
                else{
                    callBack.onError(request,response.code(),null);
                }
            }
        });
    }

    private void onTokenError(final  BaseCallBack callback ,
                              final Request request){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onTokenError(request,402);
            }
        });
    }

    private void onSuccessful(final  BaseCallBack callback ,
                              final Request request, final Object obj){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSucessful(request,obj);
            }
        });
    }

    private Request buildRequest(String url,Map<String,String> params,
                                 HttpMethodType type){
        Request.Builder builder =new Request.Builder();

        if (type == HttpMethodType.GET){
            builder.url(buildUrlParams(url,params));
            builder.get();
        }else if(type == HttpMethodType.POST){
            builder.url(url);
            RequestBody body =buildFormData(params);
            builder.post(body);
        }
        return builder.build();
    }

    private   String buildUrlParams(String url ,Map<String,String> params) {

        if(params == null)
            params = new HashMap<>(1);

        String token = MyApplication.getMyApplication().getToken();
        if(token!=null && token !="")
            params.put("token",token);


        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }

        if(url.indexOf("?")>0){
            url = url +"&"+s;
        }else{
            url = url +"?"+s;
        }

        return url;
    }


    private RequestBody buildFormData(Map<String,String> params){
        FormBody.Builder builder = new FormBody.Builder();
        if (params !=null){
            for (Map.Entry<String,String> entry:params.entrySet())
            {
                builder.add(entry.getKey(),entry.getValue());
            }
        }
        String token = MyApplication.getMyApplication().getToken();
        if (token!=null && token !="")
            builder.add("token",token);
        return builder.build();
    }
    enum HttpMethodType{
        GET,
        POST
    }
}
