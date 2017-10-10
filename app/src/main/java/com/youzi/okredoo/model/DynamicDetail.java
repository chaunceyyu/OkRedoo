package com.youzi.okredoo.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hjw on 17/3/13.
 */

public class DynamicDetail implements Serializable {

    public static final int TYPE_VIDEO = 2;
    public static final int TYPE_IMAGE = 1;

    private String viewCount;
    private String content;
    private String date;
    private String nickName;
    private String hots;//粮票
    private String uid;
    private String commentCount;
    private String rate;
    private String did;
    private String type;
    private List<DynamicImg> images;
    private String videoCover;
    private String videoURL;
    private String photo;
    private String praiseCount;
    private String isPraise;

    @SerializedName("is_record")
    private String isRecord;

    public String getViewCount() {
        return TextUtils.isEmpty(viewCount) ? "0" : viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 粮票
     *
     * @return
     */
    public String getHots() {
        return hots;
    }

    public void setHots(String hots) {
        this.hots = hots;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DynamicImg> getImages() {
        return images;
    }

    public void setImages(List<DynamicImg> images) {
        this.images = images;
    }

    public String getVideoCover() {
        return videoCover;
    }

    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(String praiseCount) {
        this.praiseCount = praiseCount;
    }

    public String getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(String isPraise) {
        this.isPraise = isPraise;
    }

    public boolean isVideo() {
        return String.valueOf(TYPE_VIDEO).equals(type);
    }

    public boolean isImage() {
        return String.valueOf(TYPE_IMAGE).equals(type);
    }

    public boolean isRecord() {
        return "1".equals(isRecord);
    }
}
