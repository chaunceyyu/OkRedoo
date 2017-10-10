package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/9.
 */

public class Setting implements Serializable {
//    forbirdMe	不让她看我的朋友圈(0不是，1是)	string
//    isStar	标星(0不是，1是)	string
//    isblack	黑名单(0不是，1是)	string
//    unlookHIm


    private String forbirdMe;
    private String isStar;
    private String isblack;
    private String unlookHIm;


    public String getForbirdMe() {
        return forbirdMe;
    }

    public void setForbirdMe(String forbirdMe) {
        this.forbirdMe = forbirdMe;
    }

    public String getIsStar() {
        return isStar;
    }

    public void setIsStar(String isStar) {
        this.isStar = isStar;
    }

    public String getIsblack() {
        return isblack;
    }

    public void setIsblack(String isblack) {
        this.isblack = isblack;
    }

    public String getUnlookHIm() {
        return unlookHIm;
    }

    public void setUnlookHIm(String unlookHIm) {
        this.unlookHIm = unlookHIm;
    }
}
