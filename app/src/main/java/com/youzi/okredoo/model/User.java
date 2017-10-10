package com.youzi.okredoo.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by hjw on 17/1/24.
 */
@Entity
public class User implements Serializable {

    private String birthday;
    private String city;
    private String coins;
    private String fans;
    private String follows;
    private String grade;
    private String hots;
    @SerializedName(value = "nickName", alternate = {"nickname"})
    private String nickName;
    private String photo;
    private String role;
    private String sex;
    private String signature;
    private String token;
    @Id
    private String uid;
    private String phone;
    private String invistCode;
    private String unumber;
    private String rongtoken;
    private String gradeName;
    private String expense;
    private String stamp;
    private String experience;
    private String isFollowFan;

    //背景
    private String background;

    //真实名字
    private String realname;
    //身份证号码
    private String idNumber;
    //frontPhoto backPhoto
    private String frontPhoto;
    private String backPhoto;
    private String profit;      //邀请佣金
    private String strangerreminder;// 0是提醒 1 是不提醒
    private boolean appreminder;// 1是提醒 0 是不提醒
    private boolean zhengdong;// 1是震动 0 是不震动

    //邀请进群是否需要验证，1需要，0不需要
    private String groupAgree;
    //新消息通知
    private boolean notify;


    private int bindPhoneStatus;
    private String cover;
    private String pwd;

    private int online;//是否在线

    public boolean getNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public boolean getZhengdong() {
        return zhengdong;
    }

    public void setZhengdong(boolean zhengdong) {
        this.zhengdong = zhengdong;
    }

    public boolean getAppreminder() {
        return appreminder;
    }

    public void setAppreminder(boolean appreminder) {
        this.appreminder = appreminder;
    }

    public String getStrangerreminder() {
        return strangerreminder;
    }

    public void setStrangerreminder(String strangerreminder) {
        this.strangerreminder = strangerreminder;
    }

    public String getFrontPhoto() {
        return frontPhoto;
    }

    public void setFrontPhoto(String frontPhoto) {
        this.frontPhoto = frontPhoto;
    }

    public String getBackPhoto() {
        return backPhoto;
    }

    public void setBackPhoto(String backPhoto) {
        this.backPhoto = backPhoto;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }


    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

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

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getRongtoken() {
        return rongtoken;
    }

    public void setRongtoken(String rongtoken) {
        this.rongtoken = rongtoken;
    }

    public String getUnumber() {
        return unumber;
    }

    public void setUnumber(String unumber) {
        this.unumber = unumber;
    }

    public String getInvistCode() {
        return invistCode;
    }

    public void setInvistCode(String invistCode) {
        this.invistCode = invistCode;
    }

    public int getBindPhoneStatus() {
        return bindPhoneStatus;
    }

    public void setBindPhoneStatus(int bindPhoneStatus) {
        this.bindPhoneStatus = bindPhoneStatus;
    }

    public String getIsFollowFan() {
        return isFollowFan;
    }

    public void setIsFollowFan(String isFollowFan) {
        this.isFollowFan = isFollowFan;
    }

    public User() {

    }

    public User(String birthday, String city, String coins, String fans, String follows,
                String grade, String hots, String nickName, String photo, String role, String sex,
                String signature, String token, String uid, String phone, String invistCode, String unumber,
                /*TODO:新加*/String background,/*TODO:直播背景*/String cover, String pwd) {
        this.birthday = birthday;
        this.city = city;
        this.coins = coins;
        this.fans = fans;
        this.follows = follows;
        this.grade = grade;
        this.hots = hots;
        this.nickName = nickName;
        this.photo = photo;
        this.role = role;
        this.sex = sex;
        this.signature = signature;
        this.token = token;
        this.uid = uid;
        this.phone = phone;
        this.invistCode = invistCode;
        this.unumber = unumber;
        this.background = background;
        this.cover = cover;
        this.pwd = pwd;
    }

    @Generated(hash = 601802758)
    public User(String birthday, String city, String coins, String fans, String follows, String grade,
            String hots, String nickName, String photo, String role, String sex, String signature, String token,
            String uid, String phone, String invistCode, String unumber, String rongtoken, String gradeName,
            String expense, String stamp, String experience, String isFollowFan, String background,
            String realname, String idNumber, String frontPhoto, String backPhoto, String profit,
            String strangerreminder, boolean appreminder, boolean zhengdong, String groupAgree, boolean notify,
            int bindPhoneStatus, String cover, String pwd, int online) {
        this.birthday = birthday;
        this.city = city;
        this.coins = coins;
        this.fans = fans;
        this.follows = follows;
        this.grade = grade;
        this.hots = hots;
        this.nickName = nickName;
        this.photo = photo;
        this.role = role;
        this.sex = sex;
        this.signature = signature;
        this.token = token;
        this.uid = uid;
        this.phone = phone;
        this.invistCode = invistCode;
        this.unumber = unumber;
        this.rongtoken = rongtoken;
        this.gradeName = gradeName;
        this.expense = expense;
        this.stamp = stamp;
        this.experience = experience;
        this.isFollowFan = isFollowFan;
        this.background = background;
        this.realname = realname;
        this.idNumber = idNumber;
        this.frontPhoto = frontPhoto;
        this.backPhoto = backPhoto;
        this.profit = profit;
        this.strangerreminder = strangerreminder;
        this.appreminder = appreminder;
        this.zhengdong = zhengdong;
        this.groupAgree = groupAgree;
        this.notify = notify;
        this.bindPhoneStatus = bindPhoneStatus;
        this.cover = cover;
        this.pwd = pwd;
        this.online = online;
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

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
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

    public String getHots() {
        return hots;
    }

    public void setHots(String hots) {
        this.hots = hots;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getGroupAgree() {
        return groupAgree;
    }

    public void setGroupAgree(String groupAgree) {
        this.groupAgree = groupAgree;
    }

    public boolean isGroupAgree() {
        return "1".equals(getGroupAgree());
    }

    public boolean isStrangerReminder() {
        return "1".equals(getStrangerreminder());
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }
}
