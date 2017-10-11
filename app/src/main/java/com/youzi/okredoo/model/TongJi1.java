package com.youzi.okredoo.model;

/**
 * Created by zhangjiajie on 2017/10/11.
 */

public class TongJi1 {

    private int count;
    private String date;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addCount(int count) {
        this.count += count;
    }

}
