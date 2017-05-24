package com.example.myshop.bean;

import java.io.Serializable;

/**
 * Created by 刘博良 on 2017/4/22.
 */

public class Wares implements Serializable{
    private int id;
    private String name;
    private String imgUrl;
    private Float sale;
    private Float price;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setSale(Float sale) {
        this.sale = sale;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Float getSale() {
        return sale;
    }

    public Float getPrice() {
        return price;
    }
}
