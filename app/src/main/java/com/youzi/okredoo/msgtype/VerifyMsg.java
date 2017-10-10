package com.youzi.okredoo.msgtype;

import android.os.Parcel;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.youzi.okredoo.net.MsgType;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * 验证消息
 * Created by zhangjiajie on 2017/7/20.
 */
@MessageTag(value = MsgType.VERIFY_MSG, flag = MessageTag.NONE)
public class VerifyMsg extends MessageContent {

    //0加好友申请，1入群申请，2拉人入群验证
    public static final int TYPE_REQUEST_FRIEND = 0;
    public static final int TYPE_REQUEST_GROUP = 1;
    public static final int TYPE_REQUEST_INVITATION = 2;

    @Expose
    protected String type;
    @Expose
    protected String message;
    @Expose
    protected String extra;

    public static final Creator<VerifyMsg> CREATOR = new Creator<VerifyMsg>() {
        public VerifyMsg createFromParcel(Parcel source) {
            return new VerifyMsg(source);
        }

        public VerifyMsg[] newArray(int size) {
            return new VerifyMsg[size];
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

    public VerifyMsg() {
    }

    public VerifyMsg(byte[] data) {
//        try {
//            String json = new String(data, "UTF-8");
//            VerifyMsg verifyMsg = new Gson().fromJson(json, VerifyMsg.class);
//            setType(verifyMsg.getType());
//            setMessage(verifyMsg.getMessage());
//            setExtra(verifyMsg.getExtra());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }

        try {
            JSONObject e = new JSONObject(jsonStr);
            if (e.has("extra")) {
                this.setExtra(e.optString("extra"));
            }
            if (e.has("message")) {
                this.setMessage(e.optString("message"));
            }
            if (e.has("type")) {
                this.setType(e.optString("type"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, this.getType());
        ParcelUtils.writeToParcel(dest, this.getMessage());
        ParcelUtils.writeToParcel(dest, this.getExtra());

    }

    public VerifyMsg(Parcel in) {
        this.setType(ParcelUtils.readFromParcel(in));
        this.setMessage(ParcelUtils.readFromParcel(in));
        this.setExtra(ParcelUtils.readFromParcel(in));

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
