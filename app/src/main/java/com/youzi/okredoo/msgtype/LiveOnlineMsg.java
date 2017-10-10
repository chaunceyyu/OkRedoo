package com.youzi.okredoo.msgtype;

import android.os.Parcel;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.net.MsgType;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * 直播间在线用户变化
 */
@MessageTag(value = MsgType.LIVE_ONLINE, flag = MessageTag.NONE)
public class LiveOnlineMsg extends MessageContent {

    private String online;
    private String users;
    private String extra;

    @Expose(serialize = false, deserialize = false)
    private ArrayList<User> onlineUsers;

    public static final Creator<LiveOnlineMsg> CREATOR = new Creator<LiveOnlineMsg>() {
        @Override
        public LiveOnlineMsg createFromParcel(Parcel source) {
            return new LiveOnlineMsg(source);
        }

        @Override
        public LiveOnlineMsg[] newArray(int size) {
            return new LiveOnlineMsg[size];
        }
    };

    public LiveOnlineMsg() {
    }

    public LiveOnlineMsg(byte[] bytes) {
        try {
            String str = new String(bytes, "UTF-8");
            JsonObject jo = new JsonParser().parse(str).getAsJsonObject();
            if (jo.has("online")) {
                online = jo.get("online").getAsString();
            }
            if (jo.has("users")) {
                users = jo.get("users").getAsString();
            }
            if (jo.has("extra")) {
                extra = jo.get("extra").getAsString();
            }
            initOnlineUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public LiveOnlineMsg(Parcel in) {
        try {
            online = in.readString();
            users = in.readString();
            extra = in.readString();
            initOnlineUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(online);
        dest.writeString(users);
        dest.writeString(extra);
    }

    private void initOnlineUsers() {
        if (!TextUtils.isEmpty(users)) {
            onlineUsers = new Gson().fromJson(users, new TypeToken<ArrayList<User>>() {}.getType());
        }
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

    /** @see #online */
    public String getOnline() {
        return online;
    }

    /** @see #onlineUsers */
    public ArrayList<User> getOnlineUsers() {
        return onlineUsers;
    }

    /** @see #extra */
    public String getExtra() {
        return extra;
    }
}
