package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by lilifeng on 2017/2/9 11:09
 * 邮箱：2402091500@qq.com.
 */
public class Star implements Serializable {
//    grade	    等级	string
//    nickName	昵称	string
//    photo	    头像	string
//    uid

    private String grade	    ;
    private String nickName  ;
    private String photo	    ;
    private String uid       ;
    /*tag点击关注*/
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return  grade	      +""+
                nickName      +""+
                photo	 	  +""+
                uid             ;
    }
}
