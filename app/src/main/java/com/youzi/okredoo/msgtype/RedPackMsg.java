package com.youzi.okredoo.msgtype;

import android.os.Parcel;
import android.text.TextUtils;

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
@MessageTag(value = MsgType.RED_PACK_MSG, flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class RedPackMsg extends MessageContent {
    private String content;
    private String rpid;
    private String name;
    private String senderName;
    private String senderGrade;
    private String senderPhoto;
    private String senderId;
    private String extra;
    private String type;
    private String redMoney;    //红包金额

    public static final Creator<RedPackMsg> CREATOR = new Creator<RedPackMsg>() {
        public RedPackMsg createFromParcel(Parcel source) {
            return new RedPackMsg(source);
        }

        public RedPackMsg[] newArray(int size) {
            return new RedPackMsg[size];
        }
    };


    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            if (!TextUtils.isEmpty(this.getContent())) {
                jsonObj.put("content", this.getEmotion(this.getContent()));
            }
            if (!TextUtils.isEmpty(this.getRpid())) {
                jsonObj.put("rpid", this.getRpid());
            }
            if (!TextUtils.isEmpty(this.getName())) {
                jsonObj.put("name", this.getName());
            }
            if (!TextUtils.isEmpty(this.getSenderName())) {
                jsonObj.put("senderName", this.getSenderName());
            }
            if (!TextUtils.isEmpty(this.getSenderGrade())) {
                jsonObj.put("senderGrade", this.getSenderGrade());
            }
            if (!TextUtils.isEmpty(this.getSenderPhoto())) {
                jsonObj.put("senderPhoto", this.getSenderPhoto());
            }
            if (!TextUtils.isEmpty(this.getSenderId())) {
                jsonObj.put("senderId", this.getSenderId());
            }

            if (!TextUtils.isEmpty(this.getExtra())) {
                jsonObj.put("extra", this.getExtra());
            }
            if (!TextUtils.isEmpty(this.getType())) {
                jsonObj.put("type", this.getType());
            }
            if (!TextUtils.isEmpty(this.getRedMoney())) {
                jsonObj.put("redMoney", this.getRedMoney());
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

    public RedPackMsg() {
    }


    public static RedPackMsg obtain(String text, String rpid, String senderId, String senderName, String senderPhoto, String type) {
        RedPackMsg model = new RedPackMsg();
        model.setName(text);
        model.setRpid(rpid);
        model.setSenderId(senderId);
        model.setSenderName(senderName);
        model.setSenderPhoto(senderPhoto);
        model.setType(type);
        return model;
    }

    public RedPackMsg(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }

        try {
            JSONObject e = new JSONObject(jsonStr);
            if (e.has("content")) {
                this.setContent(e.optString("content"));
            }
            if (e.has("rpid")) {
                this.setRpid(e.optString("rpid"));
            }
            if (e.has("name")) {
                this.setName(e.optString("name"));
            }
            if (e.has("senderName")) {
                this.setSenderName(e.optString("senderName"));
            }
            if (e.has("senderGrade")) {
                this.setSenderGrade(e.optString("senderGrade"));
            }
            if (e.has("senderPhoto")) {
                this.setSenderPhoto(e.optString("senderPhoto"));
            }
            if (e.has("senderId")) {
                this.setSenderId(e.optString("senderId"));
            }

            if (e.has("extra")) {
                this.setExtra(e.optString("extra"));
            }
            if (e.has("type")) {
                this.setType(e.optString("type"));
            }
            if (e.has("redMoney")) {
                this.setRedMoney(e.optString("redMoney"));
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
        ParcelUtils.writeToParcel(dest, this.getRedMoney());
        ParcelUtils.writeToParcel(dest, this.getType());
        ParcelUtils.writeToParcel(dest, this.getExtra());
        ParcelUtils.writeToParcel(dest, this.content);
        ParcelUtils.writeToParcel(dest, this.rpid);
        ParcelUtils.writeToParcel(dest, this.name);
        ParcelUtils.writeToParcel(dest, this.senderName);
        ParcelUtils.writeToParcel(dest, this.senderGrade);
        ParcelUtils.writeToParcel(dest, this.senderPhoto);
        ParcelUtils.writeToParcel(dest, this.senderId);
        ParcelUtils.writeToParcel(dest, this.getUserInfo());
        ParcelUtils.writeToParcel(dest, this.getMentionedInfo());
    }

    public RedPackMsg(Parcel in) {
        this.setRedMoney(ParcelUtils.readFromParcel(in));
        this.setType(ParcelUtils.readFromParcel(in));
        this.setExtra(ParcelUtils.readFromParcel(in));
        this.setContent(ParcelUtils.readFromParcel(in));
        this.setRpid(ParcelUtils.readFromParcel(in));
        this.setName(ParcelUtils.readFromParcel(in));
        this.setSenderName(ParcelUtils.readFromParcel(in));
        this.setSenderGrade(ParcelUtils.readFromParcel(in));
        this.setSenderPhoto(ParcelUtils.readFromParcel(in));
        this.setSenderId(ParcelUtils.readFromParcel(in));
        this.setUserInfo((UserInfo) ParcelUtils.readFromParcel(in, UserInfo.class));
        this.setMentionedInfo((MentionedInfo) ParcelUtils.readFromParcel(in, MentionedInfo.class));
    }


    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRpid() {
        return rpid;
    }

    public void setRpid(String rpid) {
        this.rpid = rpid;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderGrade() {
        return senderGrade;
    }

    public void setSenderGrade(String senderGrade) {
        this.senderGrade = senderGrade;
    }

    public String getRedMoney() {
        return redMoney;
    }

    public void setRedMoney(String redMoney) {
        this.redMoney = redMoney;
    }

    public int getMoney() {
        try {
            return Integer.parseInt(redMoney);
        } catch (Exception e) {
            return 0;
        }
    }
}
