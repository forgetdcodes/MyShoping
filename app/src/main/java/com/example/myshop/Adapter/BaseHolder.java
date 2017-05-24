package com.example.myshop.Adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 刘博良 on 2017/4/23.
 */

public class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private SparseArray<View> array;
    private BaseAdapter.OnClickItemListener listener;

    public BaseHolder(View itemView,BaseAdapter.OnClickItemListener listener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.listener =listener;
        array =new SparseArray<>();
    }

    public View getView(int id){
        return findView(id);
    }

    public TextView getTextView(int id){
        return findView(id);
    }

    public ImageView getImageView(int id){
        return findView(id);
    }

    public Button getButton(int id){
        return findView(id);
    }
    private<T extends View> T findView(int id){
        View view =  array.get(id);
        if (view ==null){
            view =itemView.findViewById(id);
            array.put(id,view);
        }
        return (T)view;
    }

    @Override
    public void onClick(View v) {
        listener.addOnClickItemListener(v,getLayoutPosition());
    }
}
