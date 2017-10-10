package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/21.
 */

public class Change implements Serializable {
    private String coins;
    private String eid;
    private String hots;
    private String name;



    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getHots() {
        return hots;
    }

    public void setHots(String hots) {
        this.hots = hots;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
