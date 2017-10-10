package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by houjingwei on 2017/1/28.
 */
public class Img implements Serializable {

    private String big;
    private String rate;
    private String small;

    public Img(String s, String s1) {
        this.big = s;
        this.small= s1;
    }
    public Img() {

    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }
}
