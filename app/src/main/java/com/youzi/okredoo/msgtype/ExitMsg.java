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
 * {
 * "UId": "5EL0-9CJ9-GC01-E19R",
 * "content": {
 * "extra": "{\"uid\":\"114431\",\"groupId\":\"1194\",\"operation\":\"ExitGroup\"}",
 * "message": "菲音墨缘退出了群"
 * },
 * "conversationType": "GROUP",
 * "messageDirection": "RECEIVE",
 * "messageId": 7042,
 * "objectName": "RC:InfoNtf",
 * "readReceiptInfo": {
 * "hasRespond": false,
 * "isReadReceiptMessage": false,
 * "respondUserIdList": {
 * <p>
 * }
 * },
 * "receivedStatus": {
 * "flag": 0,
 * "isDownload": false,
 * "isListened": false,
 * "isMultipleReceive": false,
 * "isRead": false,
 * "isRetrieved": false
 * },
 * "receivedTime": 1500288226927,
 * "senderUserId": "114431",
 * "sentStatus": "SENT",
 * "sentTime": 1500288226124,
 * "targetId": "1194"
 * }
 */
@MessageTag(value = MsgType.LIVE_EXIT, flag = MessageTag.NONE)
public class ExitMsg extends MessageContent {
    protected String content;
    protected String uid;
    protected String photo;
    protected String name;
    protected String grade;
    protected String role;
    protected String online;
    protected String gid;
    protected String count;
    protected String hots;
    protected String icon;
    protected String chatMsg;
    protected String time;
    protected String extra;
    public static final Creator<ExitMsg> CREATOR = new Creator() {
        public ExitMsg createFromParcel(Parcel source) {
            return new ExitMsg(source);
        }

        public ExitMsg[] newArray(int size) {
            return new ExitMsg[size];
        }
    };


    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            if (!TextUtils.isEmpty(this.getContent())) {
                jsonObj.put("content", this.getEmotion(this.getContent()));
            }
            if (!TextUtils.isEmpty(this.getUid())) {
                jsonObj.put("uid", this.getUid());
            }
            if (!TextUtils.isEmpty(this.getPhoto())) {
                jsonObj.put("photo", this.getPhoto());
            }
            if (!TextUtils.isEmpty(this.getName())) {
                jsonObj.put("name", this.getName());
            }
            if (!TextUtils.isEmpty(this.getGrade())) {
                jsonObj.put("grade", this.getGrade());
            }
            if (!TextUtils.isEmpty(this.getRole())) {
                jsonObj.put("role", this.getRole());
            }
            if (!TextUtils.isEmpty(this.getOnline())) {
                jsonObj.put("online", this.getOnline());
            }
            if (!TextUtils.isEmpty(this.getGid())) {
                jsonObj.put("gid", this.getGid());
            }
            if (!TextUtils.isEmpty(this.getTime())) {
                jsonObj.put("time", this.getTime());
            }
            if (!TextUtils.isEmpty(this.getChatMsg())) {
                jsonObj.put("chatMsg", this.getChatMsg());
            }
            if (!TextUtils.isEmpty(this.getIcon())) {
                jsonObj.put("icon", this.getIcon());
            }
            if (!TextUtils.isEmpty(this.getHots())) {
                jsonObj.put("hots", this.getHots());
            }
            if (!TextUtils.isEmpty(this.getCount())) {
                jsonObj.put("count", this.getCount());
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

    protected ExitMsg() {
    }

    public static ExitMsg obtain(String text) {
        ExitMsg model = new ExitMsg();
        model.setContent(text);
        return model;
    }

    public ExitMsg(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }

        try {
            JSONObject e = new JSONObject(jsonStr);
            LogUtil.i("chatMsgxxx", jsonStr);
            if (e.has("content")) {
                this.setContent(e.optString("content"));
            }
            if (e.has("uid")) {
                this.setUid(e.optString("uid"));
            }
            if (e.has("photo")) {
                this.setPhoto(e.optString("photo"));
            }
            if (e.has("name")) {
                this.setName(e.optString("name"));
            }
            if (e.has("grade")) {
                this.setGrade(e.optString("grade"));
            }
            if (e.has("role")) {
                this.setRole(e.optString("role"));
            }
            if (e.has("online")) {
                this.setOnline(e.optString("online"));
            }
            if (e.has("gid")) {
                this.setGid(e.optString("gid"));
            }
            if (e.has("count")) {
                this.setCount(e.optString("count"));
            }
            if (e.has("hots")) {
                this.setHots(e.optString("hots"));
            }
            if (e.has("icon")) {
                this.setIcon(e.optString("icon"));
            }
            if (e.has("chatMsg")) {
                this.setChatMsg(e.optString("chatMsg"));
            }
            if (e.has("time")) {
                this.setTime(e.optString("time"));
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
        ParcelUtils.writeToParcel(dest, this.content);


        ParcelUtils.writeToParcel(dest, this.uid);
        ParcelUtils.writeToParcel(dest, this.photo);
        ParcelUtils.writeToParcel(dest, this.name);
        ParcelUtils.writeToParcel(dest, this.grade);
        ParcelUtils.writeToParcel(dest, this.role);
        ParcelUtils.writeToParcel(dest, this.online);
        ParcelUtils.writeToParcel(dest, this.gid);
        ParcelUtils.writeToParcel(dest, this.count);
        ParcelUtils.writeToParcel(dest, this.hots);
        ParcelUtils.writeToParcel(dest, this.icon);
        ParcelUtils.writeToParcel(dest, this.chatMsg);
        ParcelUtils.writeToParcel(dest, this.time);
        ParcelUtils.writeToParcel(dest, this.getUserInfo());
        ParcelUtils.writeToParcel(dest, this.getMentionedInfo());
    }

    public ExitMsg(Parcel in) {
        this.setExtra(ParcelUtils.readFromParcel(in));
        this.setContent(ParcelUtils.readFromParcel(in));

        this.setUid(ParcelUtils.readFromParcel(in));
        this.setPhoto(ParcelUtils.readFromParcel(in));
        this.setName(ParcelUtils.readFromParcel(in));
        this.setGrade(ParcelUtils.readFromParcel(in));
        this.setRole(ParcelUtils.readFromParcel(in));
        this.setOnline(ParcelUtils.readFromParcel(in));
        this.setGid(ParcelUtils.readFromParcel(in));
        this.setCount(ParcelUtils.readFromParcel(in));
        this.setHots(ParcelUtils.readFromParcel(in));
        this.setIcon(ParcelUtils.readFromParcel(in));
        this.setChatMsg(ParcelUtils.readFromParcel(in));
        this.setTime(ParcelUtils.readFromParcel(in));

        this.setUserInfo((UserInfo) ParcelUtils.readFromParcel(in, UserInfo.class));
        this.setMentionedInfo((MentionedInfo) ParcelUtils.readFromParcel(in, MentionedInfo.class));
    }

    public ExitMsg(String content) {
        this.setContent(content);
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
        return name;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
