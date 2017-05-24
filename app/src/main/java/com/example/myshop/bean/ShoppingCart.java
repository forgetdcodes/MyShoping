package com.example.myshop.bean;

import android.util.SparseArray;

/**
 * Created by 刘博良 on 2017/5/7.
 */

public class ShoppingCart extends Wares {
    private int mCount=1;
    private boolean isCheack;

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }

    public void setCheack(boolean cheack) {
        isCheack = cheack;
    }

    public int getmCount() {
        return mCount;
    }

    public boolean isCheack() {
        return isCheack;
    }
}
