package com.example.myshop.bean;

import java.io.Serializable;

/**
 * Created by 刘博良 on 2017/4/20.
 */

public class HomeCamplain implements Serializable {

    private Long id;
    private String title;
    private Camplain cpOne;
    private Camplain cpTwo;
    private Camplain cpThree;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCpOne(Camplain cpOne) {
        this.cpOne = cpOne;
    }

    public void setCpTwo(Camplain cpTwo) {
        this.cpTwo = cpTwo;
    }

    public void setCpThree(Camplain cpThree) {
        this.cpThree = cpThree;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Camplain getCpOne() {
        return cpOne;
    }

    public Camplain getCpTwo() {
        return cpTwo;
    }

    public Camplain getCpThree() {
        return cpThree;
    }
}
