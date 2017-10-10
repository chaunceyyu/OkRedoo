package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by lilifeng on 2017/2/9 09:50
 * 邮箱：2402091500@qq.com.
 */
public class Hot implements Serializable {
//    cover	视频封面
//    gifts	打赏数量	string
//    grade	等级	string
//    nickName	昵称	string
//    photo	头像	string
//    uid	用户id	string
//    vid	视频id

    private String cover;
    private String gifts;
    private String grade;
    private String nickName;

    private String photo;
    private String uid;
    private String vid;
    private String online;
    private String lid;

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getGifts() {
        return gifts;
    }

    public void setGifts(String gifts) {
        this.gifts = gifts;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    @Override
    public String toString() {
        return cover+""+gifts+""+ grade+""+ nickName+""+ photo+""+ uid+""+ vid;
    }
}
