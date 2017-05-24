package com.example.myshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop.Http.OkHttpHelper;
import com.example.myshop.Http.SpotsCallBack;
import com.example.myshop.Http.UrlContent;
import com.example.myshop.Utils.DESUtil;
import com.example.myshop.bean.User;
import com.example.myshop.msg.LoginRespMsg;
import com.example.myshop.weidget.ClearEditText;
import com.example.myshop.weidget.MyToolBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by 刘博良 on 2017/5/18.
 */

public class LoginActivity extends AppCompatActivity {

    @ViewInject(R.id.phone_text)
    private ClearEditText textPhoneNumber;

    @ViewInject(R.id.passWord_text)
    private ClearEditText loginPassword;

    @ViewInject(R.id.loginActivity_toolBar)
    private MyToolBar toolBar;

    @ViewInject(R.id.register_text)
    private TextView registerText;

    private OkHttpHelper okHttpHelper;

    private String TAG ="LoginActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);

        initToolBar();

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }

    private void initToolBar() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @OnClick(R.id.login_button)
    public void login(View view) {


        String phone = textPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
        }

        String pwd = loginPassword.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
        }

        Map<String,String> map =new HashMap<>(2);
        map.put("phone",phone);
        map.put("password", DESUtil.encode(UrlContent.DES_KEY,pwd));
        okHttpHelper =OkHttpHelper.getOkHttpHelper();
        okHttpHelper.post(UrlContent.LOGIN, map, new SpotsCallBack<LoginRespMsg<User>>(this) {


            @Override
            public void onSucessful(Request request, LoginRespMsg<User> userLoginRespMsg) {
                if (userLoginRespMsg.getData() !=null&& userLoginRespMsg.getToken()!=null){
                    MyApplication myApplication =MyApplication.getMyApplication();
                    myApplication.putUser(userLoginRespMsg.getData(),userLoginRespMsg.getToken());
                    Log.e(TAG, String.valueOf(userLoginRespMsg));

                    if (myApplication.getIntent() ==null ){
                        setResult(RESULT_OK);
                        finish();
                    }else{
                        myApplication.jumpActivity(LoginActivity.this);
                        finish();
                    }
                }else{
                    Toast.makeText(LoginActivity.this,"小伙子，还没有注册吧？" +
                            "特么的没注册登陆个屁呀",Toast.LENGTH_SHORT).show();
                }


            }
            @Override
            public void onError(Request request, int code, Exception e) {

            }
        });
    }
}
