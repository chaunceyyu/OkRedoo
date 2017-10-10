package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by houjingwei on 2017/1/28.
 */
public class DynamicImg implements Serializable {

    private String image;
    private String rate;

    public DynamicImg(){}

    public DynamicImg(String image){
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
