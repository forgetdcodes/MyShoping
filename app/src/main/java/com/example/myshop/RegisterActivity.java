package com.example.myshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop.weidget.ClearEditText;
import com.example.myshop.weidget.MyToolBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONObject;

import cn.smssdk.SMSSDK;
import cn.smssdk.EventHandler;
import cn.smssdk.gui.RegisterPage;
import cn.smssdk.utils.SMSLog;

/**
 * Created by 刘博良 on 2017/5/20.
 */

public class RegisterActivity extends BaseActivity {

    @ViewInject(R.id.activity_register_toolBar)
    private MyToolBar myToolBar;

    @ViewInject(R.id.txtCountry)
    private TextView countryTextView;

    @ViewInject(R.id.txtCountryCode)
    private TextView countryIdText;

    @ViewInject(R.id.register_phoneEditText)
    private ClearEditText phoneEditText;

    @ViewInject(R.id.register_passwordEditText)
    private ClearEditText passwordEditText;

    private SMSEventHandler eventHandler;

    private static final String DEFAULT_COUNTRY_ID = "42";
    private String TAG ="RegisterActivity";
    private String code;
    private String phonyNumber;
    private String passWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ViewUtils.inject(this);
        initToolBar();

        SMSSDK.initSDK(this, "1e0125d148d6c", "b661afc6262c4a75f87d36d08ad64c74");
        eventHandler =new SMSEventHandler();
        SMSSDK.registerEventHandler(eventHandler);

        String[] country = this.getCurrentCountry();
        if(country != null) {
            countryIdText.setText("+"+country[1]);
            countryTextView.setText(country[0]);
        }

    }

    private void initToolBar() {
        myToolBar.getmButton().setText("下一步");
        myToolBar.getmButton().setVisibility(View.VISIBLE);

        myToolBar.setRightButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCode();

            }
        });
        myToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getCode(){
        phonyNumber =phoneEditText.getText().toString().trim().replaceAll("\\s*", "");
        passWord =passwordEditText.getText().toString().trim();
        code =countryIdText.getText().toString();
        Log.e("RegisterActivity",phonyNumber+"...."+code);

        checkPhonyNumber(phonyNumber,code);

        SMSSDK.getVerificationCode(code,phonyNumber);

    }

    private void checkPhonyNumber(String phoneNumber1,String code1){
        if (code1.startsWith("+"))
            code1 =code1.substring(1);
        Log.e(TAG,""+code1);
        if (TextUtils.isEmpty(phoneNumber1)){
            Toast.makeText(this,"把你的手机号快快交出来",Toast.LENGTH_SHORT).show();
            return;
        }

        if (code1.equals("86")){
            if (phoneNumber1.length()!=11){
                Toast.makeText(RegisterActivity.this,"笨蛋，手机号都不够11位，让我注册个屁呀",Toast.LENGTH_SHORT).show();

            }
        }
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

                        }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                            //获取验证码成功
                            afterVerificationCodeRequested((Boolean) data);

                        }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                            //返回支持发送验证码的国家列表

                        }
                    }else{
                        try {
                            ((Throwable) data).printStackTrace();
                            Throwable throwable = (Throwable) data;

                            JSONObject object = new JSONObject(
                                    throwable.getMessage());
                            String des = object.optString("detail");
                            if (!TextUtils.isEmpty(des)) {
                                Toast.makeText(RegisterActivity.this,des,Toast.LENGTH_SHORT).show();
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


    private void afterVerificationCodeRequested(boolean smart) {
        Intent intent =new Intent(this,RegisterAgain.class);

        if (code.startsWith("+"))
            code =code.substring(1);
        intent.putExtra("phony",phonyNumber);
        intent.putExtra("passWord",passWord);
        intent.putExtra("code",code);

        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    private String[] getCurrentCountry() {
        String mcc = this.getMCC();
        String[] country = null;
        if(!TextUtils.isEmpty(mcc)) {
            country = SMSSDK.getCountryByMCC(mcc);
        }

        if(country == null) {
            SMSLog.getInstance().d("no country found by MCC: " + mcc, new Object[0]);
            country = SMSSDK.getCountry("42");
        }

        return country;
    }

    private String getMCC() {
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = tm.getNetworkOperator();
        return !TextUtils.isEmpty(networkOperator)?networkOperator:tm.getSimOperator();
    }
}
