package com.example.myshop.bean;

/**
 * Created by 刘博良 on 2017/4/2.
 */

public class Tab {
    private int imageView;
    private int textView;
    private Class fragment;

    public Tab(int imageView, int textView, Class fragment) {
        this.imageView = imageView;
        this.textView = textView;
        this.fragment = fragment;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public int getTextView() {
        return textView;
    }

    public void setTextView(int textView) {
        this.textView = textView;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
