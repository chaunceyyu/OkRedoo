package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by lilifeng on 2017/2/9 10:50
 * 邮箱：2402091500@qq.com.
 */
public class Local implements Serializable {
//    grade  	等级	string
//    lid	   直播id	string
//    local	    距离	string
//    nickName	昵称	string
//    online	在线人数	string
//    photo	    头像	string
//    title	   标题	string
//    uid	   用户id	string

    private String grade    ;
    private String lid	  ;
    private String local	  ;
    private String nickName ;
    private String online   ;
    private String photo	  ;
    private String title	  ;
    private String uid	  ;


    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    @Override
    public String toString() {
        return  grade     +""+
                lid	       +""+
                local	   +""+
                nickName  +""+
                online    +""+
                photo	   +""+
                title	   +""+
                uid	   ;
    }
}
