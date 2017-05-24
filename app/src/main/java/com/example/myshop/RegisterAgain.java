package com.example.myshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop.Http.OkHttpHelper;
import com.example.myshop.Http.SpotsCallBack;
import com.example.myshop.Http.UrlContent;
import com.example.myshop.Utils.DESUtil;
import com.example.myshop.bean.User;
import com.example.myshop.msg.LoginRespMsg;
import com.example.myshop.weidget.ClearEditText;
import com.example.myshop.weidget.CountTimerView;
import com.example.myshop.weidget.MyToolBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;
import dmax.dialog.SpotsDialog;
import okhttp3.Request;

/**
 * Created by 刘博良 on 2017/5/21.
 */

public class RegisterAgain extends BaseActivity {

    @ViewInject(R.id.registerAgain_toolBar)
    private MyToolBar myToolBar;

    @ViewInject(R.id.registerAgain_textTip)
    private TextView textTip;

    @ViewInject(R.id.registerAgain_toSendButton)
    private Button toSendButton;

    @ViewInject(R.id.registerAgain_editText)
    private ClearEditText clearEditText;


    private String code;
    private String phonyNumber;
    private String passWord;

    private SMSEventHandler eventHandler;
    private OkHttpHelper okHttpHelper;
    private SpotsDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_again);
        ViewUtils.inject(this);

        initToolBar();
        getExtrData();

        eventHandler =new SMSEventHandler();
        SMSSDK.registerEventHandler(eventHandler);

        CountTimerView timerView =new CountTimerView(toSendButton);
        timerView.start();
    }

    @OnClick(R.id.registerAgain_toSendButton)
    private void onToSendClick(){
        SMSSDK.getVerificationCode(code,phonyNumber);

        textTip.setText("又给你写了一份圣旨，朕好乏");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    private void getExtrData(){
        code =getIntent().getStringExtra("code");
        phonyNumber =getIntent().getStringExtra("phony");
        passWord =getIntent().getStringExtra("passWord");

        textTip.setText("圣旨已经发送到 "+splitPhoneNum(phonyNumber)+" 上了，赶紧接旨去");

    }

    private void initToolBar() {
        myToolBar.getmButton().setText("完成");
        myToolBar.getmButton().setVisibility(View.VISIBLE);
        myToolBar.setRightButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishRegister();
            }
        });
        myToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void finishRegister(){
        String checkCode =clearEditText.getText().toString();
        if (TextUtils.isEmpty(checkCode)) {
            Toast.makeText(this, "你很调皮吆，妈的，赶紧把验证码给我填上！", Toast.LENGTH_SHORT).show();
            return;
        }
        SMSSDK.submitVerificationCode(code,phonyNumber,checkCode);
    }

    private void doRegister(){
        Map<String,String> map =new HashMap<>(2);
        map.put("phone",phonyNumber);
        map.put("password", DESUtil.encode(UrlContent.DES_KEY,passWord));
        okHttpHelper = OkHttpHelper.getOkHttpHelper();
        okHttpHelper.post(UrlContent.REG, map, new SpotsCallBack<LoginRespMsg<User>>(this) {


            @Override
            public void onSucessful(Request request, LoginRespMsg<User> userLoginRespMsg) {

                MyApplication myApplication =MyApplication.getMyApplication();
                myApplication.putUser(userLoginRespMsg.getData(),userLoginRespMsg.getToken());
                startActivity(new Intent(RegisterAgain.this,MainActivity.class));
                finish();
            }
            @Override
            public void onError(Request request, int code, Exception e) {

            }
        });
    }
    private String splitPhoneNum(String phone) {
        StringBuilder builder = new StringBuilder(phone);
        builder.reverse();
        for (int i = 4, len = builder.length(); i < len; i += 5) {
            builder.insert(i, ' ');
        }
        builder.reverse();
        return builder.toString();
    }

    class SMSEventHandler extends EventHandler{
        @Override
        public void afterEvent(final int event, final int result, final Object data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //回调完成
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            //提交验证码成功
                            doRegister();
                        }
                    }else{
                        try {
                            ((Throwable) data).printStackTrace();
                            Throwable throwable = (Throwable) data;

                            JSONObject object = new JSONObject(
                                    throwable.getMessage());
                            String des = object.optString("detail");
                            if (!TextUtils.isEmpty(des)) {
                                Toast.makeText(RegisterAgain.this,des,Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            SMSLog.getInstance().w(e);
                        }
                    }
                }
            });

        }
    }
}
