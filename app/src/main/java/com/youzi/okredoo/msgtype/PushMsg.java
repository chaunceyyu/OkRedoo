package com.youzi.okredoo.msgtype;

import android.os.Parcel;

import com.google.gson.Gson;
import com.youzi.okredoo.net.MsgType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.rong.common.ParcelUtils;
import io.rong.common.RLog;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * Created by hjw on 17/2/23.
 */
@MessageTag(value = MsgType.PUSH_MSG, flag = MessageTag.NONE)
public class PushMsg extends MessageContent {
    protected String content;
    protected String url;
    protected String type;
    protected String extra;
    private String name;
    private String photo;

    public static final Creator<PushMsg> CREATOR = new Creator() {
        public PushMsg createFromParcel(Parcel source) {
            return new PushMsg(source);
        }

        public PushMsg[] newArray(int size) {
            return new PushMsg[size];
        }
    };


    public byte[] encode() {

        return new Gson().toJson(this).getBytes();

//        JSONObject jsonObj = new JSONObject();
//
//        try {
//            if(!TextUtils.isEmpty(this.getContent())) {
//                jsonObj.put("content", this.getEmotion(this.getContent()));
//            }
//            if(!TextUtils.isEmpty(this.getUrl())) {
//                jsonObj.put("url", this.getUrl());
//            }
//            if(!TextUtils.isEmpty(this.getType())) {
//                jsonObj.put("type", this.getType());
//            }
//            if(!TextUtils.isEmpty(this.getExtra())) {
//                jsonObj.put("extra", this.getExtra());
//            }
//
//        } catch (JSONException var4) {
//            RLog.e("EnterMsg", "JSONException " + var4.getMessage());
//        }
//
//        try {
//            return jsonObj.toString().getBytes("UTF-8");
//        } catch (UnsupportedEncodingException var3) {
//            var3.printStackTrace();
//            return null;
//        }
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
        RLog.d("PushMsg", "getEmotion--" + sb.toString());
        return sb.toString();
    }

    public PushMsg() {
    }

    public static PushMsg obtain(String text) {
        PushMsg model = new PushMsg();
        model.setContent(text);
        return model;
    }

    public PushMsg(byte[] data) {

        PushMsg pushMsg = new Gson().fromJson(new String(data), PushMsg.class);

        setUrl(pushMsg.getUrl());
        setContent(pushMsg.getContent());
        setExtra(pushMsg.getExtra());
        setName(pushMsg.getName());
        setPhoto(pushMsg.getPhoto());

//        String jsonStr = null;
//
//        try {
//            jsonStr = new String(data, "UTF-8");
//        } catch (UnsupportedEncodingException var5) {
//            var5.printStackTrace();
//        }
//
//        try {
//            JSONObject e = new JSONObject(jsonStr);
//            LogUtil.i("chatMsgxxx", jsonStr);
//            if (e.has("content")) {
//                this.setContent(e.optString("content"));
//            }
//            if (e.has("url")) {
//                this.setUrl(e.optString("url"));
//            }
//            if (e.has("type")) {
//                this.setType(e.optString("type"));
//            }
//
//            if (e.has("extra")) {
//                this.setExtra(e.optString("extra"));
//            }
//
//        } catch (JSONException var4) {
//            RLog.e("EnterMsg", "JSONException " + var4.getMessage());
//        }

    }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, this.getExtra());
        ParcelUtils.writeToParcel(dest, this.content);


        ParcelUtils.writeToParcel(dest, this.url);
        ParcelUtils.writeToParcel(dest, this.type);

    }

    public PushMsg(Parcel in) {
        this.setExtra(ParcelUtils.readFromParcel(in));
        this.setContent(ParcelUtils.readFromParcel(in));

        this.setUrl(ParcelUtils.readFromParcel(in));
        this.setType(ParcelUtils.readFromParcel(in));

    }

    public PushMsg(String content) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
