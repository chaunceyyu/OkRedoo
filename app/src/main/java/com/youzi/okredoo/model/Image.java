package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by 24020 on 2017/3/6.
 */
public class Image implements Serializable {
//    big		string
//    rate		string
//    small

    private String image;
    private String rate;

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
