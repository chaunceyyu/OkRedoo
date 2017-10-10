package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/30.
 */

public class Card implements Serializable {

    private String uid;
    private String photo;
    private String nickname;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
