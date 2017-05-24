package com.example.myshop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.myshop.Adapter.RecyclerAdapter;
import com.example.myshop.Http.BaseCallBack;
import com.example.myshop.Http.OkHttpHelper;
import com.example.myshop.Http.SpotsCallBack;
import com.example.myshop.Http.UrlContent;
import com.example.myshop.R;
import com.example.myshop.WareActivity;
import com.example.myshop.bean.Banner;
import com.example.myshop.bean.Camplain;
import com.example.myshop.bean.HomeCamplain;
import com.example.myshop.weidget.DividedItemDecorition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 刘博良 on 2017/4/2.
 */

public class HomeFragment extends BaseFragment {
    private SliderLayout mDemoSlider;
    private PagerIndicator pagerIndicator;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    private List<Banner> mList;
    private OkHttpHelper okHttpHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment,null);
        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);
        pagerIndicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);
        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView_home);
        okHttpHelper =OkHttpHelper.getOkHttpHelper();
        requestImage();

        initRecyclerView();

        return view;
    }


    private void initRecyclerView() {

        okHttpHelper.get(UrlContent.CAMPAIGN_HOME, new BaseCallBack<List<HomeCamplain>>() {
            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onFailure(IOException e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSucessful(Request request, List<HomeCamplain> homeCamplains) {
                recyclerAdapter =new RecyclerAdapter(homeCamplains,getActivity());
                recyclerAdapter.setOnCamClickListener(new RecyclerAdapter.onCaomplainClickListener() {
                    @Override
                    public void onClick(View view, Camplain campaign) {
                        Intent intent =new Intent(getActivity(), WareActivity.class);
                        intent.putExtra(UrlContent.COMPAINGAIN_ID,campaign.getId());
                        startActivity(intent,true);

                    }
                });
                recyclerView.setAdapter(recyclerAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.addItemDecoration(new DividedItemDecorition());
            }


            @Override
            public void onError(Request request, int code, Exception e) {
            }

            @Override
            public void onTokenError(Request request, int code) {

            }
        });

    }

    private void requestImage(){
        String url ="http://112.124.22.238:8081/course_api/banner/query?type=1";
        mList = new ArrayList<>();

        okHttpHelper.get(url, new SpotsCallBack<List<Banner>>(getActivity()) {

            @Override
            public void onSucessful(Request request, List<Banner> banners) {
                mList =banners;
                initSlideView();
            }

            @Override
            public void onError(Request request, int code, Exception e) {

            }
        });
    }

    private void initSlideView() {

        for (Banner mBanner:mList){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView.image(mBanner.getImgUrl());
            textSliderView.description(mBanner.getName());
            textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
            mDemoSlider.addSlider(textSliderView);
        }


        mDemoSlider.setCustomIndicator(pagerIndicator);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        mDemoSlider.setDuration(3000);

    }
}
