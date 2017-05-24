package com.example.myshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.myshop.Adapter.BaseAdapter;
import com.example.myshop.Adapter.GrideAdapter;
import com.example.myshop.Adapter.HotFragment_RecyclerAdapter;
import com.example.myshop.Adapter.ListAdapter;
import com.example.myshop.Http.OkHttpHelper;
import com.example.myshop.Http.SpotsCallBack;
import com.example.myshop.bean.Banner;
import com.example.myshop.bean.CategoryList;
import com.example.myshop.bean.Page;
import com.example.myshop.bean.Wares;
import com.example.myshop.weidget.DividedGrideDecorition;
import com.lidroid.xutils.ViewUtils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myshop.R;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import okhttp3.Request;

import static com.example.myshop.Http.UrlContent.CATEGORY_LIST;
import static com.example.myshop.Http.UrlContent.WARES_HOT;
import static com.example.myshop.Http.UrlContent.WARES_LIST;

/**
 * Created by 刘博良 on 2017/4/2.
 */

public class categoryFragment extends Fragment {

    @ViewInject(R.id.first_recyclerView)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.right_recyclerView)
    private RecyclerView mRecyclerWaverView;

    @ViewInject(R.id.sliderLayout)
    private SliderLayout mDemoSlider;

    @ViewInject(R.id.refreshLayout)
    private MaterialRefreshLayout refreshLayout ;

    private OkHttpHelper mOkhttpHelper;
    private ListAdapter listAdapter;
    private GrideAdapter grideAdapter;
    private List<Wares> mList;


    private int categoryId =1;
    private int currentPage =1;
    private final int totalPage=3;
    private final int STATE_NORMAL =100;
    private final int STATE_REFUSH =101;
    private final int STATE_LOADMORE =102;
    private int state =STATE_NORMAL;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.categoryfragment,null,false);
        ViewUtils.inject(this,view);

        requestData();
        requestSliderData();
        requestWarvesData();
        initRefeshView();
        return view;
    }

    private void initSlideView(List<Banner> mList) {

        for (Banner mBanner:mList){
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.image(mBanner.getImgUrl());
            textSliderView.description(mBanner.getName());
            textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
            mDemoSlider.addSlider(textSliderView);
        }
//        mDemoSlider.setCustomIndicator(pagerIndicator);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        mDemoSlider.setDuration(3000);

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
        requestWarvesData();
    }

    private void loadmoreData(){
        state =STATE_LOADMORE;
        currentPage= ++currentPage;
        requestWarvesData();
    }
    private void requestWarvesData(){
        String url =WARES_LIST+"?categoryId="+categoryId+"&curPage="+currentPage+"&pageSize=10";
        mOkhttpHelper.get(url, new SpotsCallBack<Page>(getActivity()) {


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
    private void showDate(){
        switch (state){
            case STATE_NORMAL:
                initGrideView();
                break;
            case STATE_REFUSH:
                grideAdapter.clearData();
                grideAdapter.addData(mList);
                mRecyclerWaverView.scrollToPosition(0);
                refreshLayout.finishRefresh();
                break;
            case STATE_LOADMORE:
                int position =grideAdapter.getData().size();
                grideAdapter.addData(position,mList);
                mRecyclerWaverView.scrollToPosition(position);
                refreshLayout.finishRefreshLoadMore();
                break;
        }
    }

    private void initGrideView() {
        grideAdapter = new GrideAdapter(mList, getActivity());
        mRecyclerWaverView.setAdapter(grideAdapter);
        mRecyclerWaverView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerWaverView.addItemDecoration(new DividedGrideDecorition(getActivity()));
        mRecyclerWaverView.setItemAnimator(new DefaultItemAnimator());
    }


    private void requestSliderData(){
        String url ="http://112.124.22.238:8081/course_api/banner/query?type=1";
        mOkhttpHelper =OkHttpHelper.getOkHttpHelper();
        mOkhttpHelper.get(url, new SpotsCallBack<List<Banner>>(getActivity()) {


            @Override
            public void onSucessful(Request request, List<Banner> banners) {
                initSlideView(banners);
            }

            @Override
            public void onError(Request request, int code, Exception e) {

            }
        });
    }
    private void requestData(){
        mOkhttpHelper =OkHttpHelper.getOkHttpHelper();
        mOkhttpHelper.get(CATEGORY_LIST, new SpotsCallBack<List<CategoryList>>(getActivity()) {

            @Override
            public void onSucessful(Request request, List<CategoryList> categoryLists) {
                listAdapter =new ListAdapter(categoryLists,getActivity());
                listAdapter.setOnClickListener(new BaseAdapter.OnClickItemListener() {
                    @Override
                    public void addOnClickItemListener(View view, int position) {
                        categoryId =position+1;
                        state =STATE_NORMAL;
                        currentPage =1;
                        requestWarvesData();
                    }
                });
                mRecyclerView.setAdapter(listAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());


            }

            @Override
            public void onError(Request request, int code, Exception e) {

            }
        });
    }
}
