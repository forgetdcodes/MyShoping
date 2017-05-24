package com.example.myshop.bean;

import java.io.Serializable;

/**
 * Created by 刘博良 on 2017/4/20.
 */

public class Camplain implements Serializable {

    private Long id;
    private String title;
    private String imgUrl;

    public void setId(Long  id) {
        this.id = id;
    }

    public void setName(String name) {
        this.title = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imgUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return title;
    }

    public String getImageUrl() {
        return imgUrl;
    }
}
