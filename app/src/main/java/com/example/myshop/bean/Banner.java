package com.example.myshop.bean;

/**
 * Created by 刘博良 on 2017/4/19.
 */

public class Banner extends BaseBean{
    private  String name;
    private  String imgUrl;
    private  String description;

    public Banner(String name, String imgUrl, String description) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDescription() {
        return description;
    }
}
