package com.youzi.okredoo.msgtype;

import android.os.Parcel;
import android.text.TextUtils;

import com.youzi.okredoo.model.User;
import com.youzi.okredoo.net.MsgType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.rong.common.ParcelUtils;
import io.rong.common.RLog;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * Created by hjw on 17/2/23.
 * <p>
 * {
 * "UId": "5EL0-69L3-0C01-E19Q",
 * "content": {
 * "desc": "土豆泥ID:391601",
 * "name": "燕子@～@",
 * "photo": "http://image.tudouni.doubozhibo.com/header/9e0f27e5012a4f83bbaa11549e6c9cf1.jpg",
 * "senderUid": "83889",
 * "targetId": "https://h5.tudouni.doubozhibo.com/tudouni/html/ucenter.html?uid\u003d83889"
 * },
 * "conversationType": "GROUP",
 * "messageDirection": "RECEIVE",
 * "messageId": 6973,
 * "objectName": "RC:card",
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
 * "receivedTime": 1500287416444,
 * "senderUserId": "83889",
 * "sentStatus": "SENT",
 * "sentTime": 1500287415576,
 * "targetId": "1193"
 * }
 */
@MessageTag(value = MsgType.CARD_MSG, flag = MessageTag.ISPERSISTED | MessageTag.ISCOUNTED)
public class CardMsg extends MessageContent {

    protected String photo;
    protected String targetId;
    protected String name;
    protected String desc;
    protected String type;
    protected String senderUid;
    protected String extra;

    public static final Creator<CardMsg> CREATOR = new Creator() {
        public CardMsg createFromParcel(Parcel source) {
            return new CardMsg(source);
        }

        public CardMsg[] newArray(int size) {
            return new CardMsg[size];
        }
    };


    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            if (!TextUtils.isEmpty(this.getPhoto())) {
                jsonObj.put("photo", this.getPhoto());
            }
            if (!TextUtils.isEmpty(this.getTargetId())) {
                jsonObj.put("targetId", this.getTargetId());
            }
            if (!TextUtils.isEmpty(this.getName())) {
                jsonObj.put("name", this.getName());
            }
            if (!TextUtils.isEmpty(this.getDesc())) {
                jsonObj.put("desc", this.getDesc());
            }
            if (!TextUtils.isEmpty(this.getType())) {
                jsonObj.put("type", this.getType());
            }
            if (!TextUtils.isEmpty(this.getSenderUid())) {
                jsonObj.put("senderUid", this.getSenderUid());
            }
            if (!TextUtils.isEmpty(this.getExtra())) {
                jsonObj.put("extra", this.getExtra());
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
        RLog.d("Cardmessage", "getEmotion--" + sb.toString());
        return sb.toString();
    }

    protected CardMsg() {
    }

    public static CardMsg obtain(User fred) {
        CardMsg model = new CardMsg();
//        model.setSenderUid(App.getLoginUser().getUid());
//        model.setTargetId(Constant.SHARE_UCENTER + "?uid=" + fred.getUid());
        model.setName(fred.getNickName());
        model.setPhoto(fred.getPhoto());
        model.setDesc("土豆泥ID:" + fred.getUnumber());
        return model;
    }

    public static CardMsg obtain_private(String chatMsg, String count, String icon) {
        CardMsg model = new CardMsg();
        return model;
    }

    public CardMsg(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }

        try {
            JSONObject e = new JSONObject(jsonStr);

            if (e.has("photo")) {
                this.setPhoto(e.optString("photo"));
            }
            if (e.has("targetId")) {
                this.setTargetId(e.optString("targetId"));
            }
            if (e.has("name")) {
                this.setName(e.optString("name"));
            }
            if (e.has("desc")) {
                this.setDesc(e.optString("desc"));
            }
            if (e.has("type")) {
                this.setType(e.optString("type"));
            }
            if (e.has("senderUid")) {
                this.setSenderUid(e.optString("senderUid"));
            }
            if (e.has("extra")) {
                this.setExtra(e.optString("extra"));
            }

        } catch (JSONException var4) {
            RLog.e("EnterMsg", "JSONException " + var4.getMessage());
        }

    }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {

        ParcelUtils.writeToParcel(dest, this.photo);
        ParcelUtils.writeToParcel(dest, this.targetId);
        ParcelUtils.writeToParcel(dest, this.name);
        ParcelUtils.writeToParcel(dest, this.desc);
        ParcelUtils.writeToParcel(dest, this.type);
        ParcelUtils.writeToParcel(dest, this.senderUid);
        ParcelUtils.writeToParcel(dest, this.extra);
    }

    public CardMsg(Parcel in) {

        this.setPhoto(ParcelUtils.readFromParcel(in));
        this.setTargetId(ParcelUtils.readFromParcel(in));
        this.setName(ParcelUtils.readFromParcel(in));
        this.setDesc(ParcelUtils.readFromParcel(in));
        this.setType(ParcelUtils.readFromParcel(in));
        this.setSenderUid(ParcelUtils.readFromParcel(in));
        this.setExtra(ParcelUtils.readFromParcel(in));


    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
