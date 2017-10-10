package com.youzi.okredoo.model;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class LiveUser extends User {

    private String isControl;

    public boolean isControl() {
        return "1".equals(isControl);
    }

    public void setControl(boolean value) {
        isControl = value ? "1" : "0";
    }
}
