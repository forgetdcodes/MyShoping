package com.example.myshop.bean;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 刘博良 on 2017/4/19.
 */

public class HomeCategory extends Category {
    private int imageViewBig;
    private int imageViewUp;
    private int imageViewDown;

    public HomeCategory(String name, int imageViewBig, int imageViewUp, int imageViewDown) {
        super(name);
        this.imageViewBig = imageViewBig;
        this.imageViewUp = imageViewUp;
        this.imageViewDown = imageViewDown;
    }

    public void setImageViewBig(int imageViewBig) {
        this.imageViewBig = imageViewBig;
    }

    public void setImageViewUp(int imageViewUp) {
        this.imageViewUp = imageViewUp;
    }

    public void setImageViewDown(int imageViewDown) {
        this.imageViewDown = imageViewDown;
    }

    public int getImageViewBig() {
        return imageViewBig;
    }

    public int getImageViewUp() {
        return imageViewUp;
    }

    public int getImageViewDown() {
        return imageViewDown;
    }
}
