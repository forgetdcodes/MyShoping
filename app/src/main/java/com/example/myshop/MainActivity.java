package com.example.myshop;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.myshop.bean.Tab;
import com.example.myshop.fragment.HomeFragment;
import com.example.myshop.fragment.cartFragment;
import com.example.myshop.fragment.categoryFragment;
import com.example.myshop.fragment.hotFragment;
import com.example.myshop.fragment.mineFragment;
import com.example.myshop.weidget.FragmentTabHost;
import com.example.myshop.weidget.MyToolBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private FragmentTabHost fragmentTabHost;
    private LayoutInflater inflater;
    private List<Tab> tabList;
    private cartFragment mCartFragment;
    private MyToolBar myToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolBar = (MyToolBar) findViewById(R.id.My_ToolBar);

        iniate();

    }

    private void iniate() {
        fragmentTabHost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this,getSupportFragmentManager(),R.id.realTabcontent);

        inflater =LayoutInflater.from(this);

        Tab homeTab = new Tab(R.drawable.selector_icon_home,R.string.home,HomeFragment.class);
        Tab hotTab = new Tab(R.drawable.selector_icon_hot,R.string.hot,hotFragment.class);
        Tab categoryTab = new Tab(R.drawable.selector_icon_catagory,R.string.catagory,categoryFragment.class);
        Tab cartTab = new Tab(R.drawable.selector_icon_cart,R.string.cart,cartFragment.class);
        Tab mineTab = new Tab(R.drawable.selector_icon_user,R.string.mine,mineFragment.class);

        tabList =new ArrayList<>(5);
        tabList.add(homeTab);
        tabList.add(hotTab);
        tabList.add(categoryTab);
        tabList.add(cartTab);
        tabList.add(mineTab);

        for (Tab tab:tabList){
            View view =inflater.inflate(R.layout.tabhost_bottom_item,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.TabHostItem_ImageView);
            TextView textView = (TextView) view.findViewById(R.id.TabHostItem_TextView);
            imageView.setImageResource(tab.getImageView());
            textView.setText(tab.getTextView());
            TabHost.TabSpec tabSpec =fragmentTabHost.newTabSpec(getString(tab.getTextView()));
            tabSpec.setIndicator(view);
            fragmentTabHost.addTab(tabSpec, tab.getFragment(),null);
        }
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId==getString(R.string.cart)){
                    //进行cartFragment数据的刷新
                    if (mCartFragment==null){
                        mCartFragment = (cartFragment) getSupportFragmentManager().
                                findFragmentByTag(getString(R.string.cart));
                        if (mCartFragment !=null)
                            mCartFragment.refrenshData();
                    }
                    else mCartFragment.refrenshData();
                    if (mCartFragment!=null)
                        mCartFragment.turnToolBar();
                }
                else{
                    myToolBar.showEditView();
                    myToolBar.hideTitle();
                    myToolBar.getmButton().setVisibility(View.GONE);
                }
            }
        });
        //去掉分割线
        fragmentTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        //设置tab为第一个
        fragmentTabHost.setCurrentTab(0);
    }
}
