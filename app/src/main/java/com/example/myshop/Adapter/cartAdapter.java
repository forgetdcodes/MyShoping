package com.example.myshop.Adapter;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.myshop.R;
import com.example.myshop.Utils.cartProvider;
import com.example.myshop.bean.ShoppingCart;
import com.example.myshop.bean.Wares;
import com.example.myshop.weidget.NumberLayout;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.List;

import javax.security.auth.login.LoginException;

/**
 * Created by 刘博良 on 2017/5/7.
 */

public class cartAdapter extends BaseAdapter<ShoppingCart,BaseHolder> implements BaseAdapter.OnClickItemListener {

    private List<ShoppingCart> mLists;

    private CheckBox checkBox;
    private TextView mTotalTextView;

    private cartProvider mCartProvider;
    public cartAdapter(List<ShoppingCart> mList, Context mContext, final CheckBox checkBox, TextView mTotalTextView) {
        super(mList, mContext, R.layout.template_cart);
        mLists =mList;
        mCartProvider =new cartProvider(mContext);

        this.checkBox =checkBox;
        this.mTotalTextView =mTotalTextView;
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllCheckBox(checkBox.isChecked());
                showTotalPrice();
            }
        });

        setOnClickListener(this);
        showTotalPrice();
    }

    public List<ShoppingCart> getData(){
        return mLists;
    }

    public void addData(List<ShoppingCart> list){
        addData(0,list);
    }
    public void addData(int position,List<ShoppingCart> list){
        if (mLists!=null){
            mLists.addAll(position,list);
            notifyItemRangeChanged(position,mLists.size());
        }

    }
    public void clearData(){
        if (mLists!=null){
            mLists.clear();
            notifyItemRangeRemoved(0,mLists.size());
        }
    }

    private float getTotalPrice(){
        float sum =0;
        if (mLists!=null&&mLists.size()>0){
            for (ShoppingCart cart:mLists) {
                if (cart.isCheack())
                    sum+=(cart.getmCount()*cart.getPrice());
            }
        }
        return sum;
    }

    public void showTotalPrice(){
        float total =getTotalPrice();
        mTotalTextView.setText("合计：￥"+total);
    }
    @Override
    public void bindData(BaseHolder holder, final ShoppingCart cart) {
        holder.getTextView(R.id.text_title_temCart).setText(cart.getName());
        holder.getTextView(R.id.text_price_temCart).setText(cart.getPrice().toString());
        NumberLayout numberLayout = (NumberLayout) holder.getView(R.id.numberLayout);
        numberLayout.setValue(cart.getmCount());
        numberLayout.setOnClickButtonListener(new NumberLayout.OnClickButtonListener() {
            @Override
            public void addButtonListener() {
                cart.setmCount(cart.getmCount()+1);
                mCartProvider.update(cart);
                showTotalPrice();
            }

            @Override
            public void subButtonListener() {
                cart.setmCount(cart.getmCount()-1);
                mCartProvider.update(cart);
                showTotalPrice();
            }
        });
        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.drawee_View);
        draweeView.setImageURI(Uri.parse(cart.getImgUrl()));

        CheckBox checkBox = (CheckBox) holder.getView(R.id.checkbox);
        checkBox.setChecked(cart.isCheack());
    }

    @Override
    public void addOnClickItemListener(View view, int position) {
        ShoppingCart cart =mLists.get(position);
        cart.setCheack(!cart.isCheack());
        notifyItemChanged(position);

        checkisAll();
        showTotalPrice();
    }

    private void checkisAll(){
        int count =0;
        int checkNum =0;
        if (mLists !=null)
            count =mLists.size();
        for (ShoppingCart cart :mLists){
            if (!cart.isCheack()){
                checkBox.setChecked(false);
                break;
            }else
                checkNum =checkNum+1;
        }
        if (count == checkNum)
            checkBox.setChecked(true);
    }

    public void delCart(){
        if (mLists !=null){
            for (Iterator iterator =mLists.iterator();iterator.hasNext();){
                ShoppingCart cart = (ShoppingCart) iterator.next();
                if (cart.isCheack()){
                    int position =mLists.indexOf(cart);
                    mCartProvider.delete(cart);
                    iterator.remove();
                    notifyItemRemoved(position);//这儿有一个坑，就是将notifyItemRemoved()和notifyItemChange()方法的区别，否则会导致超出索引异常
                    Log.e("delCart","iterator");
                    Log.e("delCart","position="+position);
                }
            }
        }
    }
    public void setAllCheckBox(boolean isCheck){
        if (mLists !=null) {
            for (int i = 0; i < mLists.size(); i++) {
                mLists.get(i).setCheack(isCheck);
                notifyItemChanged(i);
            }
        }
    }
}
