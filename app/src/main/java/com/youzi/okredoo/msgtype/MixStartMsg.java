package com.youzi.okredoo.msgtype;

import android.os.Parcel;
import android.text.TextUtils;

import com.youzi.okredoo.net.MsgType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.common.RLog;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MentionedInfo;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

/**
 * Created by hjw on 17/2/23.
 */
@MessageTag(value = MsgType.MIX_START, flag = MessageTag.NONE)
public class MixStartMsg extends MessageContent {
    protected String roomName;
    protected String roomToken;
    protected String roomUid;
    protected String cid;
    protected String extra;
    public static final Creator<MixStartMsg> CREATOR = new Creator() {
        public MixStartMsg createFromParcel(Parcel source) {
            return new MixStartMsg(source);
        }

        public MixStartMsg[] newArray(int size) {
            return new MixStartMsg[size];
        }
    };


    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {

            if (!TextUtils.isEmpty(this.getRoomName())) {
                jsonObj.put("roomName", this.getRoomName());
            }
            if (!TextUtils.isEmpty(this.getRoomToken())) {
                jsonObj.put("roomToken", this.getRoomToken());
            }
            if (!TextUtils.isEmpty(this.getRoomUid())) {
                jsonObj.put("roomUid", this.getRoomUid());
            }
            if (!TextUtils.isEmpty(this.getCid())) {
                jsonObj.put("cid", this.getCid());
            }
            if (!TextUtils.isEmpty(this.getExtra())) {
                jsonObj.put("extra", this.getExtra());
            }

            if (this.getJSONUserInfo() != null) {
                jsonObj.putOpt("user", this.getJSONUserInfo());
            }

            if (this.getJsonMentionInfo() != null) {
                jsonObj.putOpt("mentionedInfo", this.getJsonMentionInfo());
            }
        } catch (JSONException var4) {
            RLog.e("EnterMsg", "JSONException " + var4.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            return null;
        }
    }


    protected MixStartMsg() {

    }

    public static MixStartMsg obtain(String cid, String roomUid, String roomName, String roomToken) {
        MixStartMsg model = new MixStartMsg();
        model.setCid(cid);
        model.setRoomUid(roomUid);
        model.setRoomName(roomName);
        model.setRoomToken(roomToken);
        return model;
    }

    public MixStartMsg(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }

        try {
            JSONObject e = new JSONObject(jsonStr);

            if (e.has("roomName")) {
                this.setRoomName(e.optString("roomName"));
            }
            if (e.has("roomToken")) {
                this.setRoomToken(e.optString("roomToken"));
            }
            if (e.has("roomUid")) {
                this.setRoomUid(e.optString("roomUid"));
            }

            if (e.has("cid")) {
                this.setCid(e.optString("cid"));
            }

            if (e.has("extra")) {
                this.setExtra(e.optString("extra"));
            }

            if (e.has("user")) {
                this.setUserInfo(this.parseJsonToUserInfo(e.getJSONObject("user")));
            }

            if (e.has("mentionedInfo")) {
                this.setMentionedInfo(this.parseJsonToMentionInfo(e.getJSONObject("mentionedInfo")));
            }
        } catch (JSONException var4) {
            RLog.e("EnterMsg", "JSONException " + var4.getMessage());
        }

    }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, this.getExtra());
        ParcelUtils.writeToParcel(dest, this.roomUid);
        ParcelUtils.writeToParcel(dest, this.roomName);
        ParcelUtils.writeToParcel(dest, this.roomToken);
        ParcelUtils.writeToParcel(dest, this.cid);
        ParcelUtils.writeToParcel(dest, this.getUserInfo());
        ParcelUtils.writeToParcel(dest, this.getMentionedInfo());
    }

    public MixStartMsg(Parcel in) {
        this.setExtra(ParcelUtils.readFromParcel(in));
        this.setRoomUid(ParcelUtils.readFromParcel(in));
        this.setRoomName(ParcelUtils.readFromParcel(in));
        this.setRoomToken(ParcelUtils.readFromParcel(in));
        this.setCid(ParcelUtils.readFromParcel(in));
        this.setUserInfo((UserInfo) ParcelUtils.readFromParcel(in, UserInfo.class));
        this.setMentionedInfo((MentionedInfo) ParcelUtils.readFromParcel(in, MentionedInfo.class));
    }


    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
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

    public String getRoomUid() {
        return roomUid;
    }

    public void setRoomUid(String roomUid) {
        this.roomUid = roomUid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
