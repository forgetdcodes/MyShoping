package com.example.myshop.Adapter;

import android.content.Context;
import android.view.View;

import com.example.myshop.R;
import com.example.myshop.bean.CategoryList;

import java.util.List;

/**
 * Created by 刘博良 on 2017/5/4.
 */

public class ListAdapter extends BaseAdapter<CategoryList,BaseHolder> {

    public ListAdapter(List<CategoryList> mList, Context mContext) {
        super(mList, mContext, R.layout.template_category_list);
    }

    @Override
    public void bindData(BaseHolder holder, CategoryList categoryList) {
        holder.getTextView(R.id.leftTextView).setText(categoryList.getName());
    }


}
