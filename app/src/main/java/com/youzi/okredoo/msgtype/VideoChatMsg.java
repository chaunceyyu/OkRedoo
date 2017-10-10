package com.youzi.okredoo.msgtype;

import android.os.Parcel;
import android.text.TextUtils;

import com.youzi.okredoo.util.LogUtil;
import com.youzi.okredoo.net.MsgType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.rong.common.ParcelUtils;
import io.rong.common.RLog;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MentionedInfo;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

/**
 * Created by hjw on 17/2/23.
 */
@MessageTag(value = MsgType.VIDEO_CHAT, flag = MessageTag.NONE)
public class VideoChatMsg extends MessageContent {
    //主播id
    protected String sid;
    //直播id
    protected String lid;
    protected String sphoto;
    protected String sname;
    protected String mid;
    protected String mphoto;
    protected String mname;

    protected String status;
    protected String extra;
    public static final Creator<VideoChatMsg> CREATOR = new Creator<VideoChatMsg>() {
        public VideoChatMsg createFromParcel(Parcel source) {
            return new VideoChatMsg(source);
        }

        public VideoChatMsg[] newArray(int size) {
            return new VideoChatMsg[size];
        }
    };


    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            if (!TextUtils.isEmpty(this.getSid())) {
                jsonObj.put("sid", this.getEmotion(this.getSid()));
            }
            if (!TextUtils.isEmpty(this.getLid())) {
                jsonObj.put("lid", this.getLid());
            }
            if (!TextUtils.isEmpty(this.getSphoto())) {
                jsonObj.put("sphoto", this.getSphoto());
            }
            if (!TextUtils.isEmpty(this.getSname())) {
                jsonObj.put("sname", this.getSname());
            }
            if (!TextUtils.isEmpty(this.getMid())) {
                jsonObj.put("mid", this.getMid());
            }
            if (!TextUtils.isEmpty(this.getMphoto())) {
                jsonObj.put("mphoto", this.getMphoto());
            }
            if (!TextUtils.isEmpty(this.getMname())) {
                jsonObj.put("mname", this.getMname());
            }

            if (!TextUtils.isEmpty(this.getStatus())) {
                jsonObj.put("status", this.getStatus());
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

    private String getEmotion(String content) {
        Pattern pattern = Pattern.compile("\\[/u([0-9A-Fa-f]+)\\]");
        Matcher matcher = pattern.matcher(content);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            int inthex = Integer.parseInt(matcher.group(1), 16);
            matcher.appendReplacement(sb, String.valueOf(Character.toChars(inthex)));
        }

        matcher.appendTail(sb);
        RLog.d("EnterMsg", "getEmotion--" + sb.toString());
        return sb.toString();
    }

    public VideoChatMsg() {
    }

    public static VideoChatMsg obtain(String text) {
        VideoChatMsg model = new VideoChatMsg();
        return model;
    }

    public VideoChatMsg(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }

        try {
            JSONObject e = new JSONObject(jsonStr);
            LogUtil.i("chatMsgxxx", jsonStr);
            if (e.has("status")) {
                this.setStatus(e.optString("status"));
            }
            if (e.has("lid")) {
                this.setLid(e.optString("lid"));
            }
            if (e.has("sid")) {
                this.setSid(e.optString("sid"));
            }
            if (e.has("sname")) {
                this.setSname(e.optString("sname"));
            }
            if (e.has("sphoto")) {
                this.setSphoto(e.optString("sphoto"));
            }
            if (e.has("mid")) {
                this.setMid(e.optString("mid"));
            }
            if (e.has("mname")) {
                this.setMname(e.optString("mname"));
            }
            if (e.has("mphoto")) {
                this.setMphoto(e.optString("mphoto"));
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
        ParcelUtils.writeToParcel(dest, this.extra);
        ParcelUtils.writeToParcel(dest, this.mphoto);
        ParcelUtils.writeToParcel(dest, this.mname);
        ParcelUtils.writeToParcel(dest, this.mid);
        ParcelUtils.writeToParcel(dest, this.sphoto);
        ParcelUtils.writeToParcel(dest, this.sname);
        ParcelUtils.writeToParcel(dest, this.sid);
        ParcelUtils.writeToParcel(dest, this.lid);
        ParcelUtils.writeToParcel(dest, this.status);
        ParcelUtils.writeToParcel(dest, this.getUserInfo());
        ParcelUtils.writeToParcel(dest, this.getMentionedInfo());
    }

    public VideoChatMsg(Parcel in) {
        this.setExtra(ParcelUtils.readFromParcel(in));
        this.setMphoto(ParcelUtils.readFromParcel(in));
        this.setMname(ParcelUtils.readFromParcel(in));
        this.setMid(ParcelUtils.readFromParcel(in));
        this.setSphoto(ParcelUtils.readFromParcel(in));
        this.setSname(ParcelUtils.readFromParcel(in));
        this.setSid(ParcelUtils.readFromParcel(in));
        this.setLid(ParcelUtils.readFromParcel(in));
        this.setStatus(ParcelUtils.readFromParcel(in));

        this.setUserInfo((UserInfo) ParcelUtils.readFromParcel(in, UserInfo.class));
        this.setMentionedInfo((MentionedInfo) ParcelUtils.readFromParcel(in, MentionedInfo.class));
    }


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getSphoto() {
        return sphoto;
    }

    public void setSphoto(String sphoto) {
        this.sphoto = sphoto;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMphoto() {
        return mphoto;
    }

    public void setMphoto(String mphoto) {
        this.mphoto = mphoto;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
