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
 * {
 * "content": "土豆姐✿ﾟ微微发了一条有趣的动态，赶紧去看看~",
 * "did": "84804",
 * "grade": "3",
 * "img": "http://image.tudouni.doubozhibo.com/cycle/66bb09c760104578aa2f89cdb5aa78e7.jpg",
 * "name": "土豆姐✿ﾟ微微",
 * "opt": "3",
 * "photo": "http://image.tudouni.doubozhibo.com/header/4477d7dd5eb7443fbc78817bef7ea396.jpg",
 * "role": "3",
 * "time": "2017-07-17 18:30:26",
 * "type": "1",
 * "uid": "91576"
 * },
 * "conversationType": "SYSTEM",
 * "messageDirection": "RECEIVE",
 * "messageId": 0,
 * "objectName": "RC:Cyc",
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
 * "receivedTime": 1500287427685,
 * "senderUserId": "-10000",
 * "sentStatus": "SENT",
 * "sentTime": 1500287427106,
 * "targetId": "-10000"
 * }
 */
@MessageTag(value = MsgType.CYC_MSG, flag = MessageTag.NONE)
public class CycMsg extends MessageContent {
    protected String uid;
    protected String photo;
    protected String name;
    protected String grade;
    protected String role;
    protected String content;
    protected String did;
    protected String type;
    protected String img;
    protected String opt;   //0赞  1评论  2礼物   3动态(此类型不存储)
    protected String time;
    protected String extra;
    public static final Creator<CycMsg> CREATOR = new Creator() {
        public CycMsg createFromParcel(Parcel source) {
            return new CycMsg(source);
        }

        public CycMsg[] newArray(int size) {
            return new CycMsg[size];
        }
    };


    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {

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
            if (!TextUtils.isEmpty(this.getContent())) {
                jsonObj.put("content", this.getEmotion(this.getContent()));
            }
            if (!TextUtils.isEmpty(this.getDid())) {
                jsonObj.put("did", this.getDid());
            }
            if (!TextUtils.isEmpty(this.getType())) {
                jsonObj.put("type", this.getType());
            }
            if (!TextUtils.isEmpty(this.getImg())) {
                jsonObj.put("img", this.getImg());
            }
            if (!TextUtils.isEmpty(this.getOpt())) {
                jsonObj.put("opt", this.getOpt());
            }
            if (!TextUtils.isEmpty(this.getTime())) {
                jsonObj.put("time", this.getTime());
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

    protected CycMsg() {
    }

    public static CycMsg obtain(String text) {
        CycMsg model = new CycMsg();
        model.setContent(text);
        return model;
    }

    public static CycMsg obtain(String chatMsg, String count, String icon) {
        CycMsg model = new CycMsg();

        return model;
    }

    public CycMsg(byte[] data) {
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
            if (e.has("content")) {
                this.setContent(e.optString("content"));
            }
            if (e.has("did")) {
                this.setDid(e.optString("did"));
            }
            if (e.has("type")) {
                this.setType(e.optString("type"));
            }
            if (e.has("img")) {
                this.setImg(e.optString("img"));
            }
            if (e.has("opt")) {
                this.setOpt(e.optString("opt"));
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
        ParcelUtils.writeToParcel(dest, this.uid);
        ParcelUtils.writeToParcel(dest, this.photo);
        ParcelUtils.writeToParcel(dest, this.name);
        ParcelUtils.writeToParcel(dest, this.grade);
        ParcelUtils.writeToParcel(dest, this.role);
        ParcelUtils.writeToParcel(dest, this.content);
        ParcelUtils.writeToParcel(dest, this.did);
        ParcelUtils.writeToParcel(dest, this.type);
        ParcelUtils.writeToParcel(dest, this.img);
        ParcelUtils.writeToParcel(dest, this.opt);
        ParcelUtils.writeToParcel(dest, this.time);
        ParcelUtils.writeToParcel(dest, this.getUserInfo());
        ParcelUtils.writeToParcel(dest, this.getMentionedInfo());
    }

    public CycMsg(Parcel in) {
        this.setExtra(ParcelUtils.readFromParcel(in));
        this.setUid(ParcelUtils.readFromParcel(in));
        this.setPhoto(ParcelUtils.readFromParcel(in));
        this.setName(ParcelUtils.readFromParcel(in));
        this.setGrade(ParcelUtils.readFromParcel(in));
        this.setRole(ParcelUtils.readFromParcel(in));
        this.setContent(ParcelUtils.readFromParcel(in));
        this.setDid(ParcelUtils.readFromParcel(in));
        this.setType(ParcelUtils.readFromParcel(in));
        this.setImg(ParcelUtils.readFromParcel(in));
        this.setOpt(ParcelUtils.readFromParcel(in));
        this.setTime(ParcelUtils.readFromParcel(in));
        this.setUserInfo((UserInfo) ParcelUtils.readFromParcel(in, UserInfo.class));
        this.setMentionedInfo((MentionedInfo) ParcelUtils.readFromParcel(in, MentionedInfo.class));
    }


    public CycMsg(String content) {
        this.setContent(content);
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return new String(encode());
    }
}
