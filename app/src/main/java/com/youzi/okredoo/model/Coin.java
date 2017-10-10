package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by houjingwei on 2017/1/18.
 */

public class Coin implements Serializable {

    private String coins;
    private String experience;


    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }
}

