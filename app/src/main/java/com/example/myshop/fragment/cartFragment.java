package com.example.myshop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.myshop.MainActivity;
import com.example.myshop.bean.ShoppingCart;
import com.example.myshop.weidget.MyToolBar;
import com.lidroid.xutils.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.myshop.Adapter.cartAdapter;
import com.example.myshop.R;
import com.example.myshop.Utils.cartProvider;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;

/**
 * Created by 刘博良 on 2017/4/2.
 */

public class cartFragment extends Fragment implements View.OnClickListener{

    public static final int ACTION_DELETE =0;
    public static final int ACTION_COMPLETE =1;
    @ViewInject(R.id.recyclerView_cart)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.bottom_checkBox)
    private CheckBox checkBox;

    @ViewInject(R.id.text_total)
    private TextView mTextTotal;

    @ViewInject(R.id.btn_order)
    private Button mBtnOrder;

    @ViewInject(R.id.btn_del)
    private Button mBtnDelete;

    @OnClick(R.id.btn_del)
    public void delCart(View view){
        mCartAdapter.delCart();
    }

    private MyToolBar myToolBar;

    private cartAdapter mCartAdapter ;
    private cartProvider mCartProcider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cartfragment,null,false);
        ViewUtils.inject(this,view);

        mCartProcider =new cartProvider(getActivity());

        showData();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity){
            MainActivity activity = (MainActivity) context;
            myToolBar = (MyToolBar) activity.findViewById(R.id.My_ToolBar);
        }
    }

    public void turnToolBar(){
        myToolBar.hideEditView();
        myToolBar.showTitle();
        myToolBar.getmButton().setVisibility(View.VISIBLE);
        myToolBar.getmButton().setOnClickListener(this);
        myToolBar.getmButton().setTag(ACTION_DELETE);
    }
    public void refrenshData(){
        mCartAdapter.clearData();
        List<ShoppingCart> list =mCartProcider.getAll();
        mCartAdapter.addData(list);
        mCartAdapter.showTotalPrice();
    }


    private void showData(){

        List<ShoppingCart> carts =mCartProcider.getAll();
        mCartAdapter =new cartAdapter(carts,getActivity(),checkBox,mTextTotal);

        mRecyclerView.setAdapter(mCartAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
    }

    private void showDelete(){
        myToolBar.getmButton().setText("完成");
        myToolBar.getmButton().setTag(ACTION_COMPLETE);
        mBtnDelete.setVisibility(View.VISIBLE);
        mBtnOrder.setVisibility(View.GONE);
        mTextTotal.setVisibility(View.GONE);
        mCartAdapter.setAllCheckBox(false);
        checkBox.setChecked(false);
    }

    private void hideDelete(){
        myToolBar.getmButton().setText("编辑");
        myToolBar.getmButton().setTag(ACTION_DELETE);
        mBtnDelete.setVisibility(View.GONE);
        mBtnOrder.setVisibility(View.VISIBLE);
        mTextTotal.setVisibility(View.VISIBLE);
        mCartAdapter.setAllCheckBox(true);
        checkBox.setChecked(true);
        mCartAdapter.showTotalPrice();
    }



    @Override
    public void onClick(View v) {

        if ((int)v.getTag()==ACTION_COMPLETE){
            hideDelete();
        }else if ((int)v.getTag()==ACTION_DELETE){
            showDelete();
        }
    }
}
