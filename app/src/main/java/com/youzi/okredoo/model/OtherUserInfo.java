package com.youzi.okredoo.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by 24020 on 2017/2/20.
 */
public class OtherUserInfo implements Serializable {
//    area		    string
//    birthday		string
//    fans	        粉丝数	string
//    follows	    关注数量	string
//    grade	        等级	string
//    gradeName		string
//    nickName	    昵称	string
//    photo	        头像	string
//    role	        角色	string
//    sex		    string
//    singtrue	    签名	string
//    uid	        用户id	string
//    unumber	    用户id	string
//    userCode


 private String city	;
 private String birthday;
 private String fans	   ;
 private String follows;
 private String grade	   ;
 private String gradeName;
 private String nickName;
 private String photo	   ;
 private String role	   ;
 private String sex	;
 private String signature;
 private String uid	   ;
 private String unumber;
 private String userCode;
 private String expense;
 private String hots;//粮票
 //TODO:背景
 private String background;

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }


    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String area) {
        this.city = area;
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

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getNickName() {
        return TextUtils.isEmpty(nickName) ? "" : nickName.replaceAll("\n", "");
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Override
    public String toString() {
        return
                        "area		==>" + city     + " ; " +
                        "birthday	==>" + birthday + " ; " +
                        "fans	    ==>" + fans     + " ; " +
                        "follows	==>" + follows  + " ; " +
                        "grade	    ==>" + grade    + " ; " +
                        "nickName	==>" + nickName + " ; " +
                        "photo	    ==>" + photo    + " ; " +
                        "role	    ==>" + role     + " ; " +
                        "signature	==>" + signature + " ; " +
                        "uid	    ==>" + uid      + " ; " +
                        "unumber	==>" + unumber  + " ; ";


    }

    public String getHots() {
        return hots;
    }

    public void setHots(String hots) {
        this.hots = hots;
    }

    public User toDbUser(){
        User u=new User();
        u.setUid(uid);
        u.setNickName(nickName);
        u.setPhoto(photo);
        u.setUnumber(unumber);
        return u;
    }
}