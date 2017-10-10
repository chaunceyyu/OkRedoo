package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by 24020 on 2017/3/8.
 */
public class GroupMemberInfo implements Serializable {
//    displayName	备注名称	string
//    grade	        等级	string
//    isAdmin		string
//    nickName	    昵称	string
//    photo	        头像	string
//    role		    string
//    uid	        用户id

    private String displayName;
    private String grade	     ;
    // 0普通 1 管理  2群主
    private String isAdmin	;
    private String nickName	 ;
    private String photo	     ;
    private String role		 ;
    private String uid	     ;


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

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
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

    @Override
    public String toString() {
        return
                "displayName==>"+displayName+" ; "+
                "grade	     ==>"+grade	     +" ; "+
                "isAdmin	==>"+isAdmin	+" ; "+
                "nickName	 ==>"+nickName	 +" ; "+
                "photo	     ==>"+photo	     +" ; "+
                "role		 ==>"+role		 +" ; "+
                "uid	     ==>"+uid	     +" ; "

        ;
    }
}
