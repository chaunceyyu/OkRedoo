package com.youzi.okredoo.msgtype;

import android.os.Parcel;
import android.text.TextUtils;

import com.youzi.okredoo.net.MsgType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
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
 * "UId": "5EL0-6CFR-CC01-E19R",
 * "content": {
 * "content": "不放过分秒的精彩，「⚜逍遥鹏💯」正在土豆泥直播开播，快去看看吧~",
 * "image": "http://image.tudouni.doubozhibo.com/header/9197539871ea480688122dd10f6d11e2.jpg",
 * "sourceName": "土豆泥",
 * "title": "⚜逍遥鹏💯带你玩转土豆泥直播",
 * "url": "https://h5.tudouni.doubozhibo.com/tudouni/html/share.html?lid\u003d599095"
 * },
 * "conversationType": "GROUP",
 * "messageDirection": "RECEIVE",
 * "messageId": 6974,
 * "objectName": "RC:Share",
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
 * "receivedTime": 1500287439453,
 * "senderUserId": "30935",
 * "sentStatus": "SENT",
 * "sentTime": 1500287438811,
 * "targetId": "1194"
 * }
 */
@MessageTag(value = MsgType.SHARE_MSG, flag = MessageTag.ISPERSISTED | MessageTag.ISCOUNTED)
public class ShareMsg extends MessageContent implements Serializable {

    protected String title;
    protected String image;
    protected String content;
    protected String url;
    protected String sourceName;
    protected String sourceIcon;
    protected String extra;

    public static final Creator<ShareMsg> CREATOR = new Creator() {
        public ShareMsg createFromParcel(Parcel source) {
            return new ShareMsg(source);
        }

        public ShareMsg[] newArray(int size) {
            return new ShareMsg[size];
        }
    };


    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            if (!TextUtils.isEmpty(this.getTitle())) {
                jsonObj.put("title", this.getTitle());
            }
            if (!TextUtils.isEmpty(this.getImage())) {
                jsonObj.put("image", this.getImage());
            }
            if (!TextUtils.isEmpty(this.getContent())) {
                jsonObj.put("content", this.getContent());
            }
            if (!TextUtils.isEmpty(this.getUrl())) {
                jsonObj.put("url", this.getUrl());
            }
            if (!TextUtils.isEmpty(this.getSourceName())) {
                jsonObj.put("sourceName", this.getSourceName());
            }
            if (!TextUtils.isEmpty(this.getSourceIcon())) {
                jsonObj.put("sourceIcon", this.getSourceIcon());
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

    public ShareMsg() {
    }

    public static ShareMsg obtain(String title, String image, String content, String url, String sourceName) {
        ShareMsg model = new ShareMsg();
        model.setTitle(title);
        model.setImage(image);
        model.setContent(content);
        model.setUrl(url);
        model.setUrl(sourceName);
        return model;
    }

    public static ShareMsg obtain_private(String chatMsg, String count, String icon) {
        ShareMsg model = new ShareMsg();
        return model;
    }

    public ShareMsg(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }


        try {
            JSONObject e = new JSONObject(jsonStr);

            if (e.has("title")) {
                this.setTitle(e.optString("title"));
            }
            if (e.has("image")) {
                this.setImage(e.optString("image"));
            }
            if (e.has("content")) {
                this.setContent(e.optString("content"));
            }
            if (e.has("url")) {
                this.setUrl(e.optString("url"));
            }
            if (e.has("sourceName")) {
                this.setSourceName(e.optString("sourceName"));
            }
            if (e.has("sourceIcon")) {
                this.setSourceIcon(e.optString("sourceIcon"));
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

        ParcelUtils.writeToParcel(dest, this.title);
        ParcelUtils.writeToParcel(dest, this.image);
        ParcelUtils.writeToParcel(dest, this.content);
        ParcelUtils.writeToParcel(dest, this.url);
        ParcelUtils.writeToParcel(dest, this.sourceName);
        ParcelUtils.writeToParcel(dest, this.sourceIcon);
        ParcelUtils.writeToParcel(dest, this.extra);
    }

    public ShareMsg(Parcel in) {

        this.setTitle(ParcelUtils.readFromParcel(in));
        this.setImage(ParcelUtils.readFromParcel(in));
        this.setContent(ParcelUtils.readFromParcel(in));
        this.setUrl(ParcelUtils.readFromParcel(in));
        this.setSourceName(ParcelUtils.readFromParcel(in));
        this.setSourceIcon(ParcelUtils.readFromParcel(in));
        this.setExtra(ParcelUtils.readFromParcel(in));


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceIcon() {
        return sourceIcon;
    }

    public void setSourceIcon(String sourceIcon) {
        this.sourceIcon = sourceIcon;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
