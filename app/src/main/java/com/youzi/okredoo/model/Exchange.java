package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/21.
 */

public class Exchange implements Serializable {
    private String coins;
    private String hots;

    public String getHots() {
        return hots;
    }

    public void setHots(String hots) {
        this.hots = hots;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }
}
