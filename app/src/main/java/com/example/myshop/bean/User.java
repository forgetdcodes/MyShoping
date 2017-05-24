package com.example.myshop.bean;

import java.io.Serializable;

/**
 * Created by 刘博良 on 2017/5/17.
 */

public class User implements Serializable {

    private Long id;
    private String email;
    private String logo_url;
    private String username;
    private String mobi;

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMobi(String mobi) {
        this.mobi = mobi;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public String getUsername() {
        return username;
    }

    public String getMobi() {
        return mobi;
    }
}
