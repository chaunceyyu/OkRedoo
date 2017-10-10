package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by 24020 on 2017/3/28.
 */
public class Myinvitatepeple implements Serializable {
//    grade	    等级	string
//    nickName	昵称	string
//    photo	    头像	string
//    uid

    private String grade	  ;
    private String nickName;
    private String photo	  ;
    private String uid;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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
