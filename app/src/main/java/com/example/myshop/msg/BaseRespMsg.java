package com.example.myshop.msg;

import java.io.Serializable;

/**
 * Created by 刘博良 on 2017/5/17.
 */

public class BaseRespMsg implements Serializable {

    public final static int STATUS_SUCCESS =1;
    public final static int STATUS_ERROR =0;
    public final static String MSG_SUCCESS ="success";

    protected int status =STATUS_SUCCESS;
    protected String message;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
