package com.example.myshop.bean;

import java.io.Serializable;

/**
 * Created by 刘博良 on 2017/4/19.
 */

public class BaseBean implements Serializable {

    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
