package com.youzi.okredoo.model;

import android.text.TextUtils;

import com.youzi.okredoo.msgtype.LiveBehaviorMsg;
import com.youzi.okredoo.msgtype.RedPackMsg;

import java.io.Serializable;

/**
 * Created by hjw on 16/5/17.
 */
public class LiveMsg implements Serializable, Cloneable {
    private String content;
    private String uid;
    private String photo;
    private String name;
    private String grade;
    private String role;
    private String online;
    private String gid;
    private String count;
    private String hots;
    private String icon;
    private String chatMsg;
    private long time = System.currentTimeMillis();
    private String extra;
    private String msgType;
    private String liveTime;
    private String nickName;
    private String sendBy;
    private String targetId;
    private String sex;     //性别，1男 else女
    private RedPackMsg redMsg;
    private LiveBehaviorMsg behaviorMsg;


    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return TextUtils.isEmpty(name) ? "" : name.replaceAll("\n", "");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getHots() {
        return hots;
    }

    public void setHots(String hots) {
        this.hots = hots;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(String chatMsg) {
        this.chatMsg = chatMsg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getMsgType() {
        return msgType == null ? "" : msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }

    public String getNickName() {

        return nickName;
    }

    public String getLiveTime() {
        return liveTime;
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }

    public RedPackMsg getRedMsg() {
        return redMsg;
    }

    public void setRedMsg(RedPackMsg redMsg) {
        this.redMsg = redMsg;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    /** @see #sex */
    public String getSex() {
        return sex;
    }

    /** @see #sex */
    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isMale() {
        return "1".equals(sex);
    }

    /** @see #behaviorMsg */
    public LiveBehaviorMsg getBehaviorMsg() {
        return behaviorMsg;
    }

    /** @see #behaviorMsg */
    public void setBehaviorMsg(LiveBehaviorMsg behaviorMsg) {
        this.behaviorMsg = behaviorMsg;
    }

    @Override
    public LiveMsg clone() throws CloneNotSupportedException {
        return (LiveMsg) super.clone();
    }
}
