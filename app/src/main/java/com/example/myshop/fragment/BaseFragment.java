package com.example.myshop.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.widget.Toast;

import com.example.myshop.LoginActivity;
import com.example.myshop.MyApplication;

/**
 * Created by 刘博良 on 2017/5/19.
 */

public class BaseFragment extends android.support.v4.app.Fragment {




    public void startActivity(Intent intent,boolean isNeedLogin){

        if (isNeedLogin == true){
            if (MyApplication.getMyApplication().getUser()!=null)
                super.startActivity(intent);
            else {
                MyApplication.getMyApplication().putIntent(intent);
                Intent intent1 =new Intent(getActivity(),LoginActivity.class);
                Toast.makeText(getActivity(),"你还没登录呢，快去登录~(@^_^@)~",Toast.LENGTH_SHORT).show();
                super.startActivity(intent1);
            }
        }else
            super.startActivity(intent);

    }
}
