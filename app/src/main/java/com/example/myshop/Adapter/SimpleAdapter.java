package com.example.myshop.Adapter;

import android.content.Context;

import java.util.List;

/**
 * Created by 刘博良 on 2017/4/25.
 */

public abstract class SimpleAdapter<T> extends BaseAdapter<T,BaseHolder> {
    public SimpleAdapter(List<T> mList, Context mContext, int resId) {
        super(mList, mContext, resId);
    }


}
