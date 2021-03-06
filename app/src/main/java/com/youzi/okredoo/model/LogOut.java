package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by houjingwei on 2017/1/18.
 */

public class LogOut implements Serializable {

    private String msg;
    private Boolean showDialog;

    public LogOut() {

    }

    public LogOut(String msg, Boolean showDialog) {
        this.msg = msg;
        this.showDialog = showDialog;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getShowDialog() {
        return showDialog;
    }

    public void setShowDialog(Boolean showDialog) {
        this.showDialog = showDialog;
    }
}

