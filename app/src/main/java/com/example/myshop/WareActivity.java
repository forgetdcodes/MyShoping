package com.example.myshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.example.myshop.Adapter.HotFragment_RecyclerAdapter;
import com.example.myshop.Http.UrlContent;
import com.example.myshop.Utils.PageUitls;
import com.example.myshop.bean.Wares;
import com.example.myshop.weidget.MyToolBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import static com.example.myshop.R.drawable.icon_grid_32;

/**
 * Created by 刘博良 on 2017/5/15.
 */

public class WareActivity extends BaseActivity implements PageUitls.OnLoadListener<Wares>,
        TabLayout.OnTabSelectedListener,View.OnClickListener{

    @ViewInject(R.id.activity_warelist_toolBar)
    private MyToolBar myToolBar;

    @ViewInject(R.id.tab_layout)
    private TabLayout tabLayout;

    @ViewInject(R.id.text_summary)
    private TextView textView;

    @ViewInject(R.id.activity_ware_refreshLayout)
    private MaterialRefreshLayout refreshLayout;

    @ViewInject(R.id.activity_warelist_recyclerView)
    private RecyclerView recyclerView;

    private PageUitls pageUitls;

    private int orderBy = 0;
    private long campaignId = 0;

    private int NORMAL_RESORT =0;
    private int PRICE_RESORT =1;
    private int SEAL_RESORT =2;

    private int ACTION_GRIDE =100;
    private int ACTION_LIST =101;

    private HotFragment_RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warelist);
        ViewUtils.inject(this);

        myToolBar.setNavigationIcon(R.drawable.icon_back_32px);


        campaignId =getIntent().getLongExtra(UrlContent.COMPAINGAIN_ID,0);

        initTab();

        getData();

        initMyToolBar();
    }

    public void initMyToolBar(){
        myToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WareActivity.this.finish();
            }
        });

        myToolBar.setRightIcon(icon_grid_32);
        myToolBar.getmButton().setText("");
        myToolBar.getmButton().setOnClickListener(this);
        myToolBar.getmButton().setTag(ACTION_LIST);
    }

    private void getData(){
        pageUitls =PageUitls.newBuilder().setUrl(UrlContent.WARES_CAMPAIN_LIST)
                .setLoadMore(true)
                .setRefreshLayout(refreshLayout)
                .putParms("campaignId",campaignId)
                .putParms("orderBy",orderBy)
                .setOnLoadListener(this)
                .builde(this);
        pageUitls.requestData(this);
    }
    private void initTab(){
        TabLayout.Tab tab=tabLayout.newTab();
        tab.setText("默认");
        tab.setTag(NORMAL_RESORT);
        tabLayout.addTab(tab);


        TabLayout.Tab tab1=tabLayout.newTab();
        tab1.setText("价格");
        tab1.setTag(PRICE_RESORT);
        tabLayout.addTab(tab1);


        TabLayout.Tab tab2=tabLayout.newTab();
        tab2.setText("销量");
        tab2.setTag(SEAL_RESORT);
        tabLayout.addTab(tab2);

        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void initeView(List<Wares> datas, int totalPage, int totalCount) {
        textView.setText("一共有"+totalCount+"个数量");
        Log.e("WareActivity",""+datas.size());
        if (recyclerAdapter ==null){
            recyclerAdapter =new HotFragment_RecyclerAdapter(datas,this,R.layout.template_hot_item_recyclerview);
            recyclerView.setAdapter(recyclerAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        else {
            recyclerAdapter.clearData();
            recyclerAdapter.addData(datas);
        }
    }

    @Override
    public void refeshView(List<Wares> datas, int totalPage, int totalCount) {
        textView.setText("一共有"+totalCount+"个数量");

        recyclerAdapter.clearData();
        recyclerAdapter.addData(datas);
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void loadMoreView(List<Wares> datas, int totalPage, int totalCount) {
        textView.setText("一共有"+totalCount+"个数量");

        recyclerAdapter.addData(datas);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        orderBy = (int) tab.getTag();
        Log.e("WareActivity", String.valueOf(pageUitls==null));
        pageUitls.putParam("orderBy",orderBy);
        pageUitls.requestData(this);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View v) {
        int action = (int) v.getTag();
        if (action ==ACTION_LIST){
            myToolBar.setRightIcon(R.drawable.icon_list_32);
            myToolBar.getmButton().setTag(ACTION_GRIDE);
//
//            recyclerAdapter.resetLayoutId(R.layout.template_category_grideview_item);
//            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        }else if(action ==ACTION_GRIDE){
            myToolBar.setRightIcon(R.drawable.icon_grid_32);
            myToolBar.getmButton().setTag(ACTION_LIST);

//            recyclerAdapter.resetLayoutId(R.layout.template_hot_item_recyclerview);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
