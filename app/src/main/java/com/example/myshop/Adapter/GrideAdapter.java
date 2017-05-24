package com.example.myshop.Adapter;

import android.content.Context;

import com.example.myshop.R;
import com.example.myshop.bean.Wares;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by 刘博良 on 2017/5/4.
 */

public class GrideAdapter extends BaseAdapter<Wares,BaseHolder> {

    private List<Wares> mLists;
    public GrideAdapter(List<Wares> mList, Context mContext) {
        super(mList, mContext, R.layout.template_category_grideview_item);
        this.mLists =mList;
    }


    public List<Wares> getData(){
        return mLists;
    }

    public void addData(List<Wares> list){
        addData(0,list);
    }
    public void addData(int position,List<Wares> list){
        mLists.addAll(position,list);
        notifyItemRangeChanged(position,mLists.size());
    }
    public void clearData(){
        mLists.clear();
        notifyItemRangeRemoved(0,mLists.size());
    }
    @Override
    public void bindData(BaseHolder holder, Wares wares) {
        holder.getTextView(R.id.text_title).setText(wares.getName());
        holder.getTextView(R.id.text_price).setText("￥"+wares.getPrice());
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.draweeView);
        simpleDraweeView.setImageURI(wares.getImgUrl());

    }
}
