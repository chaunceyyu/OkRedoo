package com.youzi.okredoo.msgtype;

import android.os.Parcel;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.youzi.okredoo.net.MsgType;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * 直播提醒消息
 * Created by zhangjiajie on 17/2/23.
 * {
 * "lid": "539779",
 * "name": "斐济 赵子豪",
 * "photo": "http://image.tudouni.doubozhibo.com/header/761b06a1153c47689565ca5ddb2d2222.jpg",
 * "type": "push",
 * "content": "正在直播，赶快去捧场吧",
 * "url": "tudouni://tudouni/playLive?lid=539779"
 * }
 */
@MessageTag(value = MsgType.LIVE_NOTIFY, flag = MessageTag.NONE)
public class LiveNotifyMsg extends MessageContent {

    @Expose
    protected String content;
    @Expose
    protected String url;
    @Expose
    protected String type;
    @Expose
    protected String extra;
    @Expose
    protected String name;
    @Expose
    protected String photo;
    @Expose
    protected String lid;

    public static final Creator<LiveNotifyMsg> CREATOR = new Creator<LiveNotifyMsg>() {
        public LiveNotifyMsg createFromParcel(Parcel source) {
            return new LiveNotifyMsg(source);
        }

        public LiveNotifyMsg[] newArray(int size) {
            return new LiveNotifyMsg[size];
        }
    };


    public byte[] encode() {

        try {
            return new Gson().toJson(this).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveNotifyMsg() {
    }

    public LiveNotifyMsg(byte[] data) {

        try {
            String json = new String(data, "UTF-8");
            LiveNotifyMsg pushMsg = new Gson().fromJson(json, LiveNotifyMsg.class);

            setUrl(pushMsg.getUrl());
            setContent(pushMsg.getContent());
            setExtra(pushMsg.getExtra());
            setName(pushMsg.getName());
            setPhoto(pushMsg.getPhoto());
            setLid(pushMsg.getLid());
            setType(pushMsg.getType());
        } catch (Exception e) {

        }


    }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, this.getExtra());
        ParcelUtils.writeToParcel(dest, this.getContent());
        ParcelUtils.writeToParcel(dest, this.getUrl());
        ParcelUtils.writeToParcel(dest, this.getType());
        ParcelUtils.writeToParcel(dest, this.getLid());
        ParcelUtils.writeToParcel(dest, this.getName());
        ParcelUtils.writeToParcel(dest, this.getPhoto());

    }

    public LiveNotifyMsg(Parcel in) {
        this.setExtra(ParcelUtils.readFromParcel(in));
        this.setContent(ParcelUtils.readFromParcel(in));
        this.setUrl(ParcelUtils.readFromParcel(in));
        this.setType(ParcelUtils.readFromParcel(in));
        this.setLid(ParcelUtils.readFromParcel(in));
        this.setName(ParcelUtils.readFromParcel(in));
        this.setPhoto(ParcelUtils.readFromParcel(in));

    }


    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }
}
