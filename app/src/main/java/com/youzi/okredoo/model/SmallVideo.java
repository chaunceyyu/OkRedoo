package com.youzi.okredoo.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by hjw on 17/1/23.
 */
public class SmallVideo implements Serializable {
    //    content	内容	string
//    cover	    图片地址	string
//    did	    视频地址	string
//    grade		string
//    nickName		string
//    photo		string
//    rate		string
//    type	    1图片，2视频	string
//    uid
    private String content;
    private String cover;
    private String did;
    private String grade;
    private String nickName;
    private String photo;
    private String rate;
    private String type;
    private String uid;
    private String videoTime;       //视频时长
    private String likeCount;       //点赞数量
    private String browerCount;     //观看数量
    private String imgNum;          //照片数量
    private String commentCount;    //评论数量
    private String rewardCount;     //礼物数量
    private String isRecord;        //是否直播回放

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVideoTime() {
        return TextUtils.isEmpty(videoTime) ? "0" : videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime;
    }

    /** @see #likeCount */
    public String getLikeCount() {
        return TextUtils.isEmpty(likeCount) ? "0" : likeCount;
    }

    /** @see #likeCount */
    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    /** @see #browerCount */
    public String getBrowerCount() {
        return TextUtils.isEmpty(browerCount) ? "0" : browerCount;
    }

    /** @see #browerCount */
    public void setBrowerCount(String browerCount) {
        this.browerCount = browerCount;
    }

    /** @see #imgNum */
    public String getImgNum() {
        return TextUtils.isEmpty(imgNum) ? "0" : imgNum;
    }

    /** @see #imgNum */
    public void setImgNum(String imgNum) {
        this.imgNum = imgNum;
    }

    /** @see #commentCount */
    public String getCommentCount() {
        return commentCount;
    }

    /** @see #commentCount */
    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    /** @see #rewardCount */
    public String getRewardCount() {
        return rewardCount;
    }

    /** @see #rewardCount */
    public void setRewardCount(String rewardCount) {
        this.rewardCount = rewardCount;
    }

    /** @see #isRecord */
    public boolean isRecord() {
        return "1".equals(isRecord);
    }
}
