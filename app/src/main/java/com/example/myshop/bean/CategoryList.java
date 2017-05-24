package com.example.myshop.bean;

/**
 * Created by 刘博良 on 2017/5/4.
 */

public class CategoryList {
    private int id;
    private String name;
    private int sort;

    public CategoryList(int id, String name, int sort) {
        this.id = id;
        this.name = name;
        this.sort = sort;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSort() {
        return sort;
    }


}
