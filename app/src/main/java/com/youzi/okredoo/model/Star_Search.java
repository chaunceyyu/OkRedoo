package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by lilifeng on 2017/2/9 16:33
 * 邮箱：2402091500@qq.com.
 */
public class Star_Search implements Serializable {
//    grade	     等级	string
//    isfollow	是否关注	string
//    nickName	昵称	string
//    photo	    头像	string
//    uid	    用户id	string


    private String grade	     ;
    private String isfollow  ;
    private String nickName   ;
    private String photo	    ;
    private String uid	    ;
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getIsfollow() {
        return isfollow;
    }

    public void setIsfollow(String isfollow) {
        this.isfollow = isfollow;
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


    @Override
    public String toString() {
        return  grade	        +""+
                isfollow      +""+
                nickName 	  +""+
                photo	   	  +""+
                uid	          ;
    }
}
