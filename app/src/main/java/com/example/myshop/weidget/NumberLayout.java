package com.example.myshop.weidget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myshop.R;

/**
 * Created by 刘博良 on 2017/5/7.
 */

public class NumberLayout extends LinearLayout implements View.OnClickListener{

    private Button mAddButton;
    private Button mSubButton;
    private TextView mNumberTextView;
    private LayoutInflater mLayoutInflater;

    private int value;
    private int maxValue;
    private int minValue;

    private OnClickButtonListener mListener;



    public interface OnClickButtonListener{
        void addButtonListener();
        void subButtonListener();
    }
    public NumberLayout(Context context) {
        this(context,null);
    }

    public NumberLayout(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public NumberLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mLayoutInflater =LayoutInflater.from(context);
        initView();
        if (attrs !=null){
            TintTypedArray tint =TintTypedArray.obtainStyledAttributes(context,attrs,
                    R.styleable.numberLayout_style,defStyleAttr,0);
            int value =tint.getInt(R.styleable.numberLayout_style_value,0);
            setValue(value);
            mNumberTextView.setText(value+"");

            int max =tint.getInt(R.styleable.numberLayout_style_maxValue,0);
            setMaxValue(max);

            int min =tint.getInt(R.styleable.numberLayout_style_minValue,0);
            setMinValue(min);
        }
    }

    private void initView() {
        View view =mLayoutInflater.inflate(R.layout.number_add_sub,this,true);
        mAddButton = (Button) view.findViewById(R.id.btn_add);
        mSubButton = (Button) view.findViewById(R.id.btn_sub);
        mNumberTextView = (TextView) view.findViewById(R.id.number_txt);


        mAddButton.setOnClickListener(this);
        mSubButton.setOnClickListener(this);
    }

    public void setOnClickButtonListener(OnClickButtonListener listener){
        this.mListener =listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                addNum();
                if (mListener!=null)
                    mListener.addButtonListener();
                break;
            case R.id.btn_sub:
                subNum();
                if (mListener !=null)
                    mListener.subButtonListener();
                break;
        }
    }

    private void addNum(){
        if (value<maxValue)
            value++;
        mNumberTextView.setText(value+"");
    }

    private void subNum(){
        if (value>0)
            value--;
        mNumberTextView.setText(value+"");
    }
    public void setValue(int value) {
        this.value = value;
        mNumberTextView.setText(value+"");
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }


    public int getValue() {
        return value;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }
}
