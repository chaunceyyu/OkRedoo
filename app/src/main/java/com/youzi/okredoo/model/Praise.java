package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by houjingwei on 2017/1/28.
 */
public class Praise implements Serializable {

    private String did;
    private String nickName;
    private String photo;
    private String uid;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
