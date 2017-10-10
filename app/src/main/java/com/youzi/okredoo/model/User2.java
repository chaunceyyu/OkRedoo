package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by 24020 on 2017/3/4.
 */
public class User2 implements Serializable {
//    birthday	生日	string
//    city	    地区	string
//    fans	    粉丝数	string
//    follows	关注数量	string
//    grade	    等级	string
//    gradeName	等级名称	string
//    nickName	昵称	string
//    phone	    手机	string
//    photo	    头像	string
//    signature	签名	string
//    uid	    用户id	string
//    unumber	用户id	string

    private String birthday	;
    private String city	    ;
    private String fans	    ;
    private String follows	;
    private String grade	    ;
    private String gradeName	;
    private String nickName	;
    private String phone	    ;
    private String photo	    ;
    private String signature	;
    private String uid	    ;
    private String unumber	;
    private String invistCode	;
    private String role;
    private String hots;
    private String coins;
    private String expense;
    private String background;
    private String profit;
    private String experience;

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

    public String getInvistCode() {
        return invistCode;
    }

    public void setInvistCode(String invistCode) {
        this.invistCode = invistCode;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHots() {
        return hots;
    }

    public void setHots(String hots) {
        this.hots = hots;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
