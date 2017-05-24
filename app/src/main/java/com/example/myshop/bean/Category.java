package com.example.myshop.bean;

/**
 * Created by 刘博良 on 2017/4/19.
 */

public class Category extends BaseBean {
    private String name;

    public Category(String name) {
        this.name = name;
    }
    public Category(String name,String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
