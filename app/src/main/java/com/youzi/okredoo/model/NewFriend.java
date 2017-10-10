package com.youzi.okredoo.model;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by 24020 on 2017/2/19.
 */
public class NewFriend implements Serializable {
//    content	    申请内容	string
//    nickName		string
//    photo		    string
//    status	    0请求添加，1已添加，2已拒绝	string
//    targetId	    申请加入id，好友为uid，群组为群id	string
//    type	        0，好友申请／1群申请	string
//    uid

    private String signature;
    private String nickName;
    private String photo;
    private String status;
    private String targetId;
    private String type;//0好友申请，1群申请，
    private String uid;
    private String hid;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
