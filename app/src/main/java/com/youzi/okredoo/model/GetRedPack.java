package com.youzi.okredoo.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhangjiajie on 2017/10/11.
 */
@Entity
public class GetRedPack {

    private String uid;
    private String rpid;
    private int count;
    private long time;

    public GetRedPack() {
    }

    @Generated(hash = 447395026)
    public GetRedPack(String uid, String rpid, int count, long time) {
        this.uid = uid;
        this.rpid = rpid;
        this.count = count;
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRpid() {
        return rpid;
    }

    public void setRpid(String rpid) {
        this.rpid = rpid;
    }


    public void setTime(long time) {
        this.time = time;
    }


    public long getTime() {
        return this.time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
