package com.youzi.okredoo.model.response;

import java.io.Serializable;

/**
 * Created by 24020 on 2017/2/16.
 */
public class UserInfo implements Serializable {
    //    area		    string
//    birthday		string
//    fans	        粉丝数	string
//    follows	    关注数量	string
//    grade	            等级	string
//    nickName	        昵称	string
//    photo	        头像	string
//    singtrue	    签名	string
//    uid	            用户id	string
//     unumber
    private String area;
    private String birthday;
    private String fans;
    private String follows;
    private String grade;
    private String nickName;
    private String photo;
    private String singtrue;
    private String uid;
    private String unumber;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getFollows() {
        return follows;
    }

    public void setFollows(String follows) {
        this.follows = follows;
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

    public String getSingtrue() {
        return singtrue;
    }

    public void setSingtrue(String singtrue) {
        this.singtrue = singtrue;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUnumber() {
        return unumber;
    }

    public void setUnumber(String unumber) {
        this.unumber = unumber;
    }
}
