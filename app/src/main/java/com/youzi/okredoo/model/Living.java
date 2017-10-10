package com.youzi.okredoo.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by hjw on 17/2/14.
 */

public class Living implements Serializable {

    private String chatroom;
    private String createDate;
    private String hots;
    private String lid;
    private String nickName;
    private String online;
    private String photo;
    private String tdid;
    private String pushUrl;
    private String title;
    private String uid;
    private String roomName;
    private String roomToken;

    /** 直播状态    0:直播，1：直播结束 */
    private String liveStatus;

    private String channelKey;
    private String grade;
    private String city;
    private String cover;
    private String tid;
    private String liveHots;

    private String liveTime;


    public String getChatroom() {
        return chatroom;
    }

    public void setChatroom(String chatroom) {
        this.chatroom = chatroom;
    }

    public String getHots() {
        return hots;
    }

    public void setHots(String hots) {
        this.hots = hots;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getNickName() {
        return TextUtils.isEmpty(nickName) ? "" : nickName.replaceAll("\n", "");
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTdid() {
        return tdid;
    }

    public void setTdid(String tdid) {
        this.tdid = tdid;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomToken() {
        return roomToken;
    }

    public void setRoomToken(String roomToken) {
        this.roomToken = roomToken;
    }

    /** @see #liveStatus */
    public String getLiveStatus() {
        return liveStatus;
    }

    /** @see #liveStatus */
    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    /** @see #liveStatus */
    public boolean isLiving() {
        return !isLiveEnded();
    }

    /** @see #liveStatus */
    public boolean isLiveEnded() {
        return "1".equals(liveStatus);
    }

    /** @see #channelKey */
    public String getChannelKey() {
        return channelKey;
    }

    /** @see #channelKey */
    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }

    /** @see #grade */
    public String getGrade() {
        return grade;
    }

    /** @see #grade */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /** @see #city */
    public String getCity() {
        return city;
    }

    /** @see #city */
    public void setCity(String city) {
        this.city = city;
    }

    /** @see #cover */
    public String getCover() {
        return cover;
    }

    /** @see #cover */
    public void setCover(String cover) {
        this.cover = cover;
    }

    /** @see #tid */
    public String getTid() {
        return tid;
    }

    /** @see #tid */
    public void setTid(String tid) {
        this.tid = tid;
    }

    /** @see #liveHots */
    public String getLiveHots() {
        return liveHots;
    }

    /** @see #liveHots */
    public void setLiveHots(String liveHots) {
        this.liveHots = liveHots;
    }

    public String getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }
}
