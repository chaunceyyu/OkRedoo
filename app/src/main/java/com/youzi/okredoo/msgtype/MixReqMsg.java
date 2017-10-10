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
@MessageTag(value = MsgType.MIX_REQ, flag = MessageTag.NONE)
public class MixReqMsg extends MessageContent {
    protected String uid;
    protected String reqUid;
    protected String reqName;
    protected String reqPhoto;
    protected String extra;
    protected String playUrl;
    public static final Creator<MixReqMsg> CREATOR = new Creator() {
        public MixReqMsg createFromParcel(Parcel source) {
            return new MixReqMsg(source);
        }

        public MixReqMsg[] newArray(int size) {
            return new MixReqMsg[size];
        }
    };


    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {

            if (!TextUtils.isEmpty(this.getUid())) {
                jsonObj.put("uid", this.getUid());
            }

            if (!TextUtils.isEmpty(this.getPlayUrl())) {
                jsonObj.put("playUrl", this.getUid());
            }
            if (!TextUtils.isEmpty(this.getReqName())) {
                jsonObj.put("reqName", this.getReqName());
            }
            if (!TextUtils.isEmpty(this.getReqPhoto())) {
                jsonObj.put("reqPhoto", this.getReqPhoto());
            }
            if (!TextUtils.isEmpty(this.getReqUid())) {
                jsonObj.put("reqUid", this.getReqUid());
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


    protected MixReqMsg() {

    }

    public static MixReqMsg obtain(String uid, String reqName, String reqPhoto) {
        MixReqMsg model = new MixReqMsg();
        model.setReqUid(uid);
        model.setReqName(reqName);
        model.setReqPhoto(reqPhoto);
        return model;
    }

    public MixReqMsg(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }

        try {
            JSONObject e = new JSONObject(jsonStr);

            if (e.has("uid")) {
                this.setUid(e.optString("uid"));
            }
            if (e.has("playUrl")) {
                this.setPlayUrl(e.optString("playUrl"));
            }
            if (e.has("reqUid")) {
                this.setReqUid(e.optString("reqUid"));
            }
            if (e.has("reqName")) {
                this.setReqName(e.optString("reqName"));
            }
            if (e.has("reqPhoto")) {
                this.setReqPhoto(e.optString("reqPhoto"));
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
        ParcelUtils.writeToParcel(dest, this.uid);
        ParcelUtils.writeToParcel(dest, this.playUrl);
        ParcelUtils.writeToParcel(dest, this.reqName);
        ParcelUtils.writeToParcel(dest, this.reqPhoto);
        ParcelUtils.writeToParcel(dest, this.reqUid);
        ParcelUtils.writeToParcel(dest, this.getUserInfo());
        ParcelUtils.writeToParcel(dest, this.getMentionedInfo());
    }

    public MixReqMsg(Parcel in) {
        this.setExtra(ParcelUtils.readFromParcel(in));
        this.setUid(ParcelUtils.readFromParcel(in));
        this.setPlayUrl(ParcelUtils.readFromParcel(in));
        this.setReqName(ParcelUtils.readFromParcel(in));
        this.setReqPhoto(ParcelUtils.readFromParcel(in));
        this.setReqUid(ParcelUtils.readFromParcel(in));
        this.setUserInfo((UserInfo) ParcelUtils.readFromParcel(in, UserInfo.class));
        this.setMentionedInfo((MentionedInfo) ParcelUtils.readFromParcel(in, MentionedInfo.class));
    }


    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    public String getReqPhoto() {
        return reqPhoto;
    }

    public void setReqPhoto(String reqPhoto) {
        this.reqPhoto = reqPhoto;
    }

    public String getReqUid() {
        return reqUid;
    }

    public void setReqUid(String reqUid) {
        this.reqUid = reqUid;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }
}
