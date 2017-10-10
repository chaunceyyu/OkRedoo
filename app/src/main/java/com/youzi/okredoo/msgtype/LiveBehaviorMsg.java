package com.youzi.okredoo.msgtype;

import android.os.Parcel;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.net.MsgType;

import java.io.UnsupportedEncodingException;

import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * 直播间用户行为
 */
@MessageTag(value = MsgType.LIVE_BEHAVIOR, flag = MessageTag.NONE)
public class LiveBehaviorMsg extends MessageContent {

    public static final String TYPE_FOLLOW = "7";           //关注
    public static final String TYPE_SHARE = "8";            //分享
    public static final String TYPE_ACTIVATE = "9";         //点亮
    public static final String TYPE_SHOP = "10";            //进入小店
    public static final String TYPE_BUY = "11";             //购买商品
    public static final String TYPE_GOOD = "12";            //进入商品页
    public static final String TYPE_SET_CONTROL = "13";     //设置场控

    private String uid;
    private String name;
    private String grade;
    private String type;
    private String content;
    private String forUrl;      //跳转url

    public static final Creator<LiveBehaviorMsg> CREATOR = new Creator<LiveBehaviorMsg>() {
        @Override
        public LiveBehaviorMsg createFromParcel(Parcel source) {
            return new LiveBehaviorMsg(source);
        }

        @Override
        public LiveBehaviorMsg[] newArray(int size) {
            return new LiveBehaviorMsg[size];
        }
    };

    public LiveBehaviorMsg() {
    }

    public LiveBehaviorMsg(byte[] bytes) {
        try {
            String str = new String(bytes, "UTF-8");
            JsonObject jo = new JsonParser().parse(str).getAsJsonObject();
            if (jo.has("uid")) {
                uid = jo.get("uid").getAsString();
            }
            if (jo.has("name")) {
                name = jo.get("name").getAsString();
            }
            if (jo.has("grade")) {
                grade = jo.get("grade").getAsString();
            }
            if (jo.has("type")) {
                type = jo.get("type").getAsString();
            }
            if (jo.has("content")) {
                content = jo.get("content").getAsString();
            }
            if (jo.has("forUrl")) {
                forUrl = jo.get("forUrl").getAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public LiveBehaviorMsg(Parcel in) {
        try {
            uid = in.readString();
            name = in.readString();
            grade = in.readString();
            type = in.readString();
            content = in.readString();
            forUrl = in.readString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(name);
        dest.writeString(grade);
        dest.writeString(type);
        dest.writeString(content);
        dest.writeString(forUrl);
    }

    @Override
    public byte[] encode() {
        try {
            return new Gson().toJson(this).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static LiveBehaviorMsg obtain(User user, String type, String content) {
        LiveBehaviorMsg msg = new LiveBehaviorMsg();
        msg.uid = user.getUid();
        msg.name = user.getNickName();
        msg.grade = user.getGrade();
        msg.type = type;
        msg.content = content;
        return msg;
    }

    /**
     * @see #uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @see #name
     */
    public String getName() {
        return name;
    }

    /**
     * @see #grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * @see #type
     */
    public String getType() {
        return type;
    }

    /**
     * @see #content
     */
    public String getContent() {
        return content;
    }

    /**
     * @see #forUrl
     */
    public String getForUrl() {
        return forUrl;
    }

    /**
     * @see #forUrl
     */
    public void setForUrl(String forUrl) {
        this.forUrl = forUrl;
    }

    public String obtainContent() {
        switch (type) {
            case TYPE_FOLLOW:
                return "关注了主播";
            case TYPE_SHARE:
                return "分享了直播间";
            case TYPE_ACTIVATE:
                return "点亮了直播间 ";
            case TYPE_SHOP:
            case TYPE_GOOD:
                return "正在前往主播的小店购买商品";
            case TYPE_BUY:
                return TextUtils.isEmpty(content) ? "在主播的小店成功付款消费了" : content;
            case TYPE_SET_CONTROL:
                return "被主播设为场控";
            default:
                return "";
        }
    }
}
