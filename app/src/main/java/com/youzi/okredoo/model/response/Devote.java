package com.youzi.okredoo.model.response;

import java.io.Serializable;

/**
 * Created by 24020 on 2017/4/15.
 */
public class Devote implements Serializable {
//    hots		string
//    rank
    private String hots;
    private String rank;

    public String getHots() {
        return hots;
    }

    public void setHots(String hots) {
        this.hots = hots;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
