package com.example.myshop.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.myshop.Adapter.BaseHolder;
import com.example.myshop.Adapter.HotFragment_RecyclerAdapter;
import com.example.myshop.Http.OkHttpHelper;
import com.example.myshop.Http.SpotsCallBack;
import com.example.myshop.R;
import com.example.myshop.WareDetailActivity;
import com.example.myshop.bean.Page;
import com.example.myshop.bean.Wares;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import okhttp3.Request;

import static com.example.myshop.Http.UrlContent.WARES_HOT;

/**
 * Created by 刘博良 on 2017/4/2.
 */

public class hotFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private List<Wares> mList;
    private HotFragment_RecyclerAdapter adapter;
    private OkHttpHelper okHttpHelper;
    private MaterialRefreshLayout refreshLayout;

    private int currentPage =1;
    private final int totalPage=3;
    private final int STATE_NORMAL =100;
    private final int STATE_REFUSH =101;
    private final int STATE_LOADMORE =102;
    private int state =100;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.hotfragment_layout,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.hotFragment_recyclerView_id);
        okHttpHelper =OkHttpHelper.getOkHttpHelper();
        refreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh);


        initRefeshView();
        getData();


        return view;
    }


    private void initRefeshView(){
        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

                refushData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (currentPage<=totalPage){
                    loadmoreData();
                }else{
                    Toast.makeText(getActivity(),"亲，没有更多数据了",Toast.LENGTH_SHORT).show();
                    refreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    private void refushData(){
        state =STATE_REFUSH;
        currentPage =1;
        getData();
    }

    private void loadmoreData(){
        state =STATE_LOADMORE;
        currentPage= ++currentPage;
        getData();
    }

    private void showDate(){
        switch (state){
            case STATE_NORMAL:
                initView();
                break;
            case STATE_REFUSH:
                adapter.clearData();
                adapter.addData(mList);
                recyclerView.scrollToPosition(0);
                refreshLayout.finishRefresh();
                break;
            case STATE_LOADMORE:
                int position =adapter.getData().size();
                adapter.addData(position,mList);
                recyclerView.scrollToPosition(position);
                refreshLayout.finishRefreshLoadMore();
                break;
        }
    }

    private void initView() {
        adapter =new HotFragment_RecyclerAdapter(mList,getActivity(),
                R.layout.template_hot_item_recyclerview);
        adapter.setOnItemClickListener(new HotFragment_RecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Wares wares =adapter.getItem(position);
                Intent intent =new Intent(getActivity(), WareDetailActivity.class);
                intent.putExtra("Wares",wares);
                startActivity(intent,true);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        recyclerView.setAdapter(new com.example.myshop.Adapter.BaseAdapter<Wares,
//                BaseHolder>(mList,getActivity(),R.layout.template_hot_item_recyclerview ) {
//            @Override
//            public void bindData(BaseHolder holder, Wares wares) {
//                SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.
//                        getView(R.id.simpleDraweeView);
//                simpleDraweeView.setImageURI(Uri.parse(wares.getImgUrl()));
////
//                holder.getTextView(R.id.black_title).setText(wares.getName());
//                holder.getTextView(R.id.price).setText(wares.getPrice().toString());
//
//            }
//        });
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void getData(){
        String url =WARES_HOT+"?curPage="+currentPage+"&pageSize=10";
        okHttpHelper.get(url, new SpotsCallBack<Page>(getActivity()) {


            @Override
            public void onSucessful(Request request, Page page) {
                Log.e("hotFragment", String.valueOf(page==null));
                mList =page.getList();
                currentPage =page.getCurrentPage();
                showDate();
            }

            @Override
            public void onError(Request request, int code, Exception e) {

            }
        });
    }
}
