package com.example.myshop.Http;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.myshop.LoginActivity;
import com.example.myshop.MyApplication;
import com.example.myshop.Utils.PerferenceUtils;

import java.io.IOException;

import dmax.dialog.SpotsDialog;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 刘博良 on 2017/4/19.
 */

public abstract class SpotsCallBack<T> extends BaseCallBack<T> {

    private SpotsDialog spotsDialog;
    private Context context;

    public SpotsCallBack(Context context) {
        spotsDialog =new SpotsDialog(context);
        this.context =context;
    }

    private void showDialog() {
        spotsDialog.show();
    }
    private void dismissDialog()
    {
        if (spotsDialog !=null)
            spotsDialog.dismiss();
    }

    public void setDialogMessage(String message){
        spotsDialog.setMessage(message);
    }

    @Override
    public void onBeforeRequest(Request request) {
        showDialog();
    }

    @Override
    public void onFailure(IOException e) {

    }

    @Override
    public void onResponse(Response response) {
        dismissDialog();
    }

    @Override
    public void onTokenError(Request request, int code) {
        Toast.makeText(context,"没有登录或者身份过期，请重新登录",Toast.LENGTH_SHORT).show();
//        Intent intent =new Intent(context, LoginActivity.class);
//        MyApplication.getMyApplication().jumpActivity(context);
//
//        MyApplication.getMyApplication().clearUser();
    }
}
