package com.youzi.okredoo.msgtype;

import android.os.Parcel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.net.MsgType;

import java.util.ArrayList;

import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * 直播间top user变化
 */
@MessageTag(value = MsgType.LIVE_TOPFANS, flag = MessageTag.NONE)
public class LiveTopFansMsg extends MessageContent {

    private String content;

    @Expose(serialize = false, deserialize = false)
    private ArrayList<User> topUsers;

    public static final Creator<LiveTopFansMsg> CREATOR = new Creator<LiveTopFansMsg>() {
        @Override
        public LiveTopFansMsg createFromParcel(Parcel source) {
            return new LiveTopFansMsg(source);
        }

        @Override
        public LiveTopFansMsg[] newArray(int size) {
            return new LiveTopFansMsg[size];
        }
    };

    public LiveTopFansMsg() {
    }

    public LiveTopFansMsg(byte[] bytes) {
        content = new String(bytes);
        initTopFans();
    }

    public LiveTopFansMsg(Parcel in) {
        content = in.readString();
        initTopFans();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
    }

    private void initTopFans() {
        topUsers = new ArrayList<>();
        try {
            JsonParser parser = new JsonParser();
            JsonObject jo = parser.parse(content).getAsJsonObject();
            JsonArray arr = parser.parse(jo.get("content").getAsString()).getAsJsonArray();
            for (int i = 0; i < arr.size(); i++) {
                User user = new User();
                JsonObject o = arr.get(i).getAsJsonObject();
                user.setUid(o.get("uid").getAsString());
                user.setNickName(o.get("nickname").getAsString());
                user.setPhoto(o.get("photo").getAsString());
                user.setGrade(o.get("grade").getAsString());
                topUsers.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] encode() {
        return new Gson().toJson(this).getBytes();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * @see #content
     */
    public String getContent() {
        return content;
    }

    /**
     * @see #topUsers
     */
    public ArrayList<User> getTopUsers() {
        return topUsers;
    }
}
