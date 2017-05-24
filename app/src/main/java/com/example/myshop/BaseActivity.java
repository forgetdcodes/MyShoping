package com.example.myshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by 刘博良 on 2017/5/19.
 */

public class BaseActivity extends AppCompatActivity {

    public void startActivity(Intent intent,boolean isNeedLogin){

        if (isNeedLogin == true){
            if (MyApplication.getMyApplication().getUser()!=null)
                super.startActivity(intent);
            else {
                MyApplication.getMyApplication().putIntent(intent);
                Intent intent1 =new Intent(this,LoginActivity.class);
                Toast.makeText(this,"你还没登录呢，快去登录~(@^_^@)~",Toast.LENGTH_SHORT).show();
                super.startActivity(intent1);
            }
        }else
            super.startActivity(intent);

    }
}
