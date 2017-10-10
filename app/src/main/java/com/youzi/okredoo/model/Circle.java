package com.youzi.okredoo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 24020 on 2017/3/6.
 */
public class Circle implements Serializable {
//            uid           ": "52",
//            sex           ": "0",
//            grade          ": "1",
//            nickName          ": "丁丁",
//            photo         ": "http://dev-user-header.oss-cn-hangzhou.aliyuncs.com/2ffce119bd704be0ab13af8d9fec13a1.jpg",
//            did           ": 78,
//            cover         ": null,
//            commentCount      ": "0",
//            content           ": "工作",
//            date              ": "2017-03-23 22:00:26",
//            hots          ": "0",
//            praiseCount           ": "2",
//            rate              ": "0.56",
//            type          ": "1",
//            videoCover        ": "",
//            videoURL          ": "",
//            viewCount         ": "10",
    private String uid         ;
    private String sex         ;
    private String grade       ;
    private String nickName    ;
    private String photo       ;
    private String did         ;
    private String cover       ;
    private String commentCount;
    private String content     ;
    private String date        ;
    private String hots        ;
    private String praiseCount ;
    private String rate        ;
    private String type        ;
    private String videoCover  ;
    private String videoURL    ;
    private String viewCount   ;
    private List<DynamicImg> images	   ;

    @SerializedName("is_record")
    private String isRecord;    //是否直播回放

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
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

    public String getHots() {
        return hots;
    }

    public void setHots(String hots) {
        this.hots = hots;
    }

    public String getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(String praiseCount) {
        this.praiseCount = praiseCount;
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

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public List<DynamicImg> getImages() {
        return images;
    }

    public void setImages(List<DynamicImg> images) {
        this.images = images;
    }

    public String getIsRecord() {
        return isRecord;
    }

    public void setIsRecord(String isRecord) {
        this.isRecord = isRecord;
    }

    public boolean isRecord() {
        return "1".equals(isRecord);
    }

    @Override
    public String toString() {
        return
                "commentCount	 ="+commentCount	+"--->"+
                "content		 ="+content		 +"--->"+
                "date		     ="+date		     +"--->"+
                "did		     ="+did		     +"--->"+
                "hots		     ="+hots		     +"--->"+
                "images	         ="+images	     +"--->"+
                "nickName		 ="+nickName		 +"--->"+
                "photo		     ="+photo		     +"--->"+
                "praiseCount	 ="+praiseCount	+"--->"+
                "rate		     ="+rate		     +"--->"+
                "type		     ="+type		     +"--->"+
                "uid		     ="+uid		     +"--->"+
                "videoCover	     ="+videoCover	+"--->"+
                "videoURL        ="+videoURL+"--->"

        ;
    }
}
