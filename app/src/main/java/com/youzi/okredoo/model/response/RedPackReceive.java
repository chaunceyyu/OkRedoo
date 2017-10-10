package com.youzi.okredoo.model.response;

import java.io.Serializable;

/**
 * Created by 24020 on 2017/3/20.
 */
public class RedPackReceive implements Serializable {
//    nickName	    总数	string
//    photo	        销毁时间	string
//    receiveCount	领取数量	string
//    time	        我是否领取0未，1已	string
//    uid
    private String nickName	  ;
    private String photo	      ;
    private String receiveCount;
    private String time	      ;
    private String uid;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getReceiveCount() {
        return receiveCount;
    }

    public void setReceiveCount(String receiveCount) {
        this.receiveCount = receiveCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
