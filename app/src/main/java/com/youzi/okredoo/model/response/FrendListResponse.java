package com.youzi.okredoo.model.response;

import java.io.Serializable;

/**
 * Created by 24020 on 2017/2/17.
 */
public class FrendListResponse implements Serializable {
//    displayName	    备注名称
//    grade	            等级	string
//    nickName	        昵称	string
//    photo	            头像	string
//    role	            角色	string
//    uid
    private String displayName;
    private String grade	     ;
    private String nickName	 ;
    private String photo	     ;
    private String role	     ;
    private String uid;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
