package com.example.myshop.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.myshop.Http.OkHttpHelper;
import com.example.myshop.Http.SpotsCallBack;
import com.example.myshop.bean.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Request;

import static com.example.myshop.Http.UrlContent.WARES_HOT;

/**
 * Created by 刘博良 on 2017/5/10.
 */

public class PageUitls {
    private OkHttpHelper okHttpHelper;
    private final int STATE_NORMAL =100;
    private final int STATE_REFUSH =101;
    private final int STATE_LOADMORE =102;
    private int state =100;

    private static Builder builder;


    private PageUitls(){
        initRefeshView(builder.mContext);
        okHttpHelper =OkHttpHelper.getOkHttpHelper();
    }
    public interface OnLoadListener<T>{
        void initeView(List<T> datas,int totalPage,int totalCount);
        void refeshView(List<T> datas,int totalPage,int totalCount);
        void loadMoreView(List<T> datas,int totalPage,int totalCount);
    }


    public void  putParam(String key,Object value){
        builder.parmes.put(key,value);

    }

    private void initRefeshView(final Context context){
        builder.refreshLayout.setLoadMore(builder.loadMore);
        builder.refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

                refushData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (builder.currentPage<=builder.totalPage){
                    loadmoreData();
                }else{
                    Toast.makeText(context,"亲，没有更多数据了",Toast.LENGTH_SHORT).show();
                    builder.refreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }
    private void refushData(){
        state =STATE_REFUSH;
        builder.currentPage =1;
        requestData(builder.mContext);
    }

    private void loadmoreData(){
        state =STATE_LOADMORE;
        builder.currentPage= ++builder.currentPage;
        requestData(builder.mContext);
    }
    public void requestData(Context context){
        okHttpHelper.get(buildUrl(), new SpotsCallBack<Page>(context) {


            @Override
            public void onSucessful(Request request, Page page) {
                Log.e("hotFragment", String.valueOf(page==null));

                builder.currentPage =page.getCurrentPage();
                showDate(page.getList(),builder.totalPage,builder.totalPage);
            }

            @Override
            public void onError(Request request, int code, Exception e) {

            }
        });
    }

    private <T>void showDate(List<T> datas,int totalPage,int totalCount){
        switch (state){
            case STATE_NORMAL:
                if (builder.onLoadListener!=null){
                    builder.onLoadListener.initeView(datas,totalPage,totalCount);
                }
                break;
            case STATE_REFUSH:
                if (builder.onLoadListener!=null){
                    builder.onLoadListener.refeshView(datas,totalPage,totalCount);
                    builder.refreshLayout.finishRefresh();
                }
                break;
            case STATE_LOADMORE:
                if (builder.onLoadListener!=null){
                    builder.onLoadListener.loadMoreView(datas,totalPage,totalCount);
                    builder.refreshLayout.finishRefreshLoadMore();
                }
                break;
        }
    }

    private String buildUrl(){
        return builder.url+"?"+buildUrlParms();
    }
    private String buildUrlParms(){
        HashMap<String,Object> map =builder.parmes;
        map.put("curPage",builder.currentPage);
        map.put("pageSize",builder.totalPage);
        StringBuffer sb =new StringBuffer();
        for (Map.Entry<String,Object> entry :map.entrySet()) {
            sb.append(entry.getKey()+"="+entry.getValue());
            sb.append("&");
        }
        String s=sb.toString();
        if (s.endsWith("&"))
            s=s.substring(0,s.length()-1);
        return s;
    }
    public static Builder newBuilder(){
        builder =new Builder();
        return builder;
    }

    public static class Builder{
        private String url;
        private MaterialRefreshLayout refreshLayout;
        private boolean loadMore;
        private int currentPage =1;
        private int totalPage=3;

        private HashMap<String,Object> parmes =new HashMap<>(5);
        private OnLoadListener onLoadListener;
        public Context mContext;

        public PageUitls builde(Context context){
            this.mContext =context;
            valid();
            return new PageUitls();
        }

        private void valid(){


            if(this.mContext==null)
                throw  new RuntimeException("content can't be null");

            if(this.url==null || "".equals(this.url))
                throw  new RuntimeException("url can't be  null");

            if(this.refreshLayout==null)
                throw  new RuntimeException("MaterialRefreshLayout can't be  null");
        }
        public Builder setOnLoadListener(OnLoadListener onLoadListener){
            this.onLoadListener =onLoadListener;
            return builder;
        }

        public Builder putParms(String key,Object value){
            parmes.put(key,value);
            return builder;
        }

        public Builder setUrl(String url){
            this.url =url;
            return builder;
        }
        public Builder setRefreshLayout(MaterialRefreshLayout refreshLayout){
            this.refreshLayout =refreshLayout;
            return builder;
        }

        public Builder setLoadMore(boolean loadMore){
            this.loadMore =loadMore;
            return builder;
        }
        public Builder setCurrentPage(int currPage){
            this.currentPage =currPage;
            return builder;
        }
        public Builder setTotalPage(int totalPage){
            this.totalPage =totalPage;
            return builder;
        }
    }
}
