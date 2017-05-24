package com.example.myshop.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop.R;
import com.example.myshop.Utils.cartProvider;
import com.example.myshop.bean.ShoppingCart;
import com.example.myshop.bean.Wares;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by 刘博良 on 2017/4/22.
 */

public class HotFragment_RecyclerAdapter extends
        RecyclerView.Adapter<HotFragment_RecyclerAdapter.ViewHolder> {

    private List<Wares> mLists;
    private LayoutInflater layoutInflater;
    private cartProvider mCartProvider;
    private Context mContext;
    public int layoutId;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
         void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener =listener;
    }
    public HotFragment_RecyclerAdapter(List<Wares> Lists, Context context,int layoutId) {
        this.mLists = Lists;
        layoutInflater =LayoutInflater.from(context);
        mCartProvider =new cartProvider(context);
        mContext =context;
        this.layoutId =layoutId;
    }

    public List<Wares> getData(){
        return mLists;
    }

    public Wares getItem(int position){
        return mLists.get(position);
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
    public HotFragment_RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =layoutInflater.inflate(layoutId,null);
        return new ViewHolder(view);
    }

    public void resetLayoutId(int id){
        this.layoutId =id;
        notifyItemRangeChanged(0,mLists.size());
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Wares wares = mLists.get(position);
        holder.setListener(listener);
        holder.simpleView.setImageURI(wares.getImgUrl());
        holder.title.setText(wares.getName());
        holder.price.setText(wares.getPrice().toString());
        holder.mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCartProvider.put(wares);
                Toast.makeText(mContext,"购物车表示很开心",Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public int getItemCount() {
        Log.e("Hotadapter", String.valueOf(mLists==null));
        return mLists.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView simpleView;
        private TextView title;
        private TextView price;
        private Button mBuyButton;
        private OnItemClickListener listener;

        public void setListener(OnItemClickListener listener){
            this.listener =listener;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(v,getLayoutPosition());
                }
            });
            simpleView = (SimpleDraweeView) itemView.findViewById(R.id.simpleDraweeView);
            title = (TextView) itemView.findViewById(R.id.black_title);
            price = (TextView) itemView.findViewById(R.id.price);
            mBuyButton = (Button) itemView.findViewById(R.id.Buy_Button);
        }
    }
}
