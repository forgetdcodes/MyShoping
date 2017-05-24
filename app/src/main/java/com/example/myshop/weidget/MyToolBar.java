package com.example.myshop.weidget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myshop.R;

/**
 * Created by 刘博良 on 2017/4/3.
 */

public class MyToolBar extends Toolbar {

    private LayoutInflater inflater;
    private EditText editText;
    private TextView textView;
    private Button mButton;
    private View view;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public MyToolBar(Context context) {
        this(context,null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public MyToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public MyToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
        setContentInsetsRelative(10,10);
        
        if (attrs != null){
        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                R.styleable.MyToolBar, defStyleAttr, 0);

        final Drawable rightIcon = a.getDrawable(R.styleable.MyToolBar_rightButton);
        if (rightIcon != null) {
            mButton.setBackground(rightIcon);
        }

        final Boolean isShowEditView = a.getBoolean(R.styleable.MyToolBar_isShowEditView,false);
        if (isShowEditView !=false){
            editText.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }

        a.recycle();

        }
    }
    public void setRightIcon(int rightIcon){
        if (mButton !=null){
            mButton.setBackgroundResource(rightIcon);
            mButton.setVisibility(View.VISIBLE);
        }
    }
    private void initView() {
        if (view== null) {
            inflater =LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.toolbar_layout, null);
            editText = (EditText) view.findViewById(R.id.toolBar_editText);
            textView = (TextView) view.findViewById(R.id.tooBar_textView);
            mButton = (Button) view.findViewById(R.id.toolBar_mButton);


            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL);
            addView(view, lp);
        }
    }

    public Button getmButton(){
        return this.mButton;
    }

    public void showEditView(){
        if (editText !=null){
            editText.setVisibility(View.VISIBLE);
        }
    }

    public void hideEditView(){
        if (editText !=null){
            editText.setVisibility(View.GONE);
        }
    }
    public void showTitle(){
        if (textView !=null){
            textView.setVisibility(View.VISIBLE);
        }
    }

    public void hideTitle(){
        if (textView !=null){
            textView.setVisibility(View.GONE);
        }
    }
    public void setRightButtonClickListener(OnClickListener listener){
        mButton.setOnClickListener(listener);
    }
    @Override
    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initView();
        if (textView !=null){
            textView.setText(title);
            textView.setVisibility(View.VISIBLE);
        }
    }
}
