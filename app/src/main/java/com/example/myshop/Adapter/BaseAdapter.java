package com.example.myshop.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 刘博良 on 2017/4/23.
 */

public abstract class BaseAdapter<T,H extends BaseHolder>
        extends RecyclerView.Adapter<BaseHolder>{

    protected List<T> mList=null;
    protected Context mContext;
    protected LayoutInflater layoutInflater;
    protected int resId;
    protected OnClickItemListener listener=null;
    public interface OnClickItemListener{
         void addOnClickItemListener(View view,int position);
    }

    public void setOnClickListener(OnClickItemListener clickItemListener){
        listener=clickItemListener;

    }
    public BaseAdapter(List<T> mList, Context mContext,int resId) {
        this.mList = mList;
        this.mContext = mContext;
        layoutInflater =LayoutInflater.from(mContext);
        this.resId =resId;
    }



    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =layoutInflater.inflate(resId,null,false);
        return new BaseHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        T t =mList.get(position);
        bindData(holder,t);
    }

    public abstract void bindData(BaseHolder holder, T t) ;

    @Override
    public int getItemCount() {
        if (mList==null)
            return 0;
        else return mList.size();
    }
}
