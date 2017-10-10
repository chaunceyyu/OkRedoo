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
@MessageTag(value = MsgType.PUSH_GOOD,flag = MessageTag.NONE)
public class PushGoodMsg extends MessageContent {

    protected String goodId;
    protected String goodName;
    protected String url;
    protected String price;
    protected String img;
    protected String extra;
    public static final Creator<PushGoodMsg> CREATOR = new Creator() {
        public PushGoodMsg createFromParcel(Parcel source) {
            return new PushGoodMsg(source);
        }

        public PushGoodMsg[] newArray(int size) {
            return new PushGoodMsg[size];
        }
    };


    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            if(!TextUtils.isEmpty(this.getGoodId())) {
                jsonObj.put("goodId", this.getEmotion(this.getGoodId()));
            }
            if(!TextUtils.isEmpty(this.getGoodName())) {
                jsonObj.put("goodName", this.getGoodName());
            }
            if(!TextUtils.isEmpty(this.getUrl())) {
                jsonObj.put("url", this.getUrl());
            }
            if(!TextUtils.isEmpty(this.getPrice())) {
                jsonObj.put("price", this.getPrice());
            }
            if(!TextUtils.isEmpty(this.getImg())) {
                jsonObj.put("img", this.getImg());
            }
            if(!TextUtils.isEmpty(this.getExtra())) {
                jsonObj.put("extra", this.getExtra());
            }
            if(this.getJSONUserInfo() != null) {
                jsonObj.putOpt("user", this.getJSONUserInfo());
            }

            if(this.getJsonMentionInfo() != null) {
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

        while(matcher.find()) {
            int inthex = Integer.parseInt(matcher.group(1), 16);
            matcher.appendReplacement(sb, String.valueOf(Character.toChars(inthex)));
        }

        matcher.appendTail(sb);
        RLog.d("EnterMsg", "getEmotion--" + sb.toString());
        return sb.toString();
    }

    public PushGoodMsg() {
    }

    public static PushGoodMsg obtain(String text) {
        PushGoodMsg model = new PushGoodMsg();
        return model;
    }

    public PushGoodMsg(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }

        try {
            JSONObject e = new JSONObject(jsonStr);
            LogUtil.i("chatMsgxxx",jsonStr);
            if(e.has("goodId")) {
                this.setGoodId(e.optString("goodId"));
            }
            if(e.has("goodName")) {
                this.setGoodName(e.optString("goodName"));
            }
            if(e.has("url")) {
                this.setUrl(e.optString("url"));
            }
            if(e.has("price")) {
                this.setPrice(e.optString("price"));
            }
            if(e.has("img")) {
                this.setImg(e.optString("img"));
            }
            if(e.has("extra")) {
                this.setExtra(e.optString("extra"));
            }

            if(e.has("user")) {
                this.setUserInfo(this.parseJsonToUserInfo(e.getJSONObject("user")));
            }

            if(e.has("mentionedInfo")) {
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
        ParcelUtils.writeToParcel(dest, this.goodId);
        ParcelUtils.writeToParcel(dest, this.goodName);
        ParcelUtils.writeToParcel(dest, this.url);
        ParcelUtils.writeToParcel(dest, this.price);
        ParcelUtils.writeToParcel(dest, this.img);
        ParcelUtils.writeToParcel(dest, this.getUserInfo());
        ParcelUtils.writeToParcel(dest, this.getMentionedInfo());
    }

    public PushGoodMsg(Parcel in) {
        this.setExtra(ParcelUtils.readFromParcel(in));
        this.setGoodId(ParcelUtils.readFromParcel(in));
        this.setGoodName(ParcelUtils.readFromParcel(in));
        this.setUrl(ParcelUtils.readFromParcel(in));
        this.setPrice(ParcelUtils.readFromParcel(in));
        this.setImg(ParcelUtils.readFromParcel(in));
        this.setUserInfo((UserInfo) ParcelUtils.readFromParcel(in, UserInfo.class));
        this.setMentionedInfo((MentionedInfo) ParcelUtils.readFromParcel(in, MentionedInfo.class));
    }


    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
