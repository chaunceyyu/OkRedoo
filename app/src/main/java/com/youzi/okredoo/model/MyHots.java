package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * 我的收益(粮票)
 */
public class MyHots implements Serializable {

    private String totalHots;
    private String cash;
    private String hots;

    public String getTotalHots() {
        return totalHots;
    }

    public void setTotalHots(String totalHots) {
        this.totalHots = totalHots;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getHots() {
        return hots;
    }

    public void setHots(String hots) {
        this.hots = hots;
    }

    public double getCashNumber() {
        try {
            return Double.parseDouble(cash);
        } catch (Exception e) {
            return 0d;
        }
    }
}
