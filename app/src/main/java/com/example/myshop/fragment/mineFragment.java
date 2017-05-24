package com.example.myshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop.LoginActivity;
import com.example.myshop.MyApplication;
import com.example.myshop.R;
import com.example.myshop.bean.User;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 刘博良 on 2017/4/2.
 */

public class mineFragment extends Fragment {

    @ViewInject(R.id.image_head)
    private CircleImageView circleImageView;

    @ViewInject(R.id.text_username)
    private TextView loginText;

    @ViewInject(R.id.toOutLogin)
    private Button outLogin;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.minfragment,container,false);
        ViewUtils.inject(this,view);
        return view;
    }

    @OnClick(value = {R.id.text_username,R.id.image_head})
    private void toLogin(View view){
        if (MyApplication.getMyApplication().getUser() ==null){
            Intent intent =new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent,1);
        }else
            Toast.makeText(getActivity(),"已经登陆过了，不要再点我了╭(╯^╰)╮",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        User user =MyApplication.getMyApplication().getUser();
        showUser(user);
    }

    @OnClick(R.id.toOutLogin)
    public void logout(View view){
        MyApplication.getMyApplication().clearUser();
        showUser(null);
    }

    private void showUser(User user) {
        if (user !=null){
            if (user.getLogo_url()!=null&&user.getLogo_url()!="")
                Picasso.with(getActivity()).load(user.getLogo_url()).into(circleImageView);
            loginText.setText(user.getUsername());
            outLogin.setVisibility(View.VISIBLE);
        }else{
            loginText.setText("请登录，么么哒");
            outLogin.setVisibility(View.GONE);
        }
    }
}
