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
import io.rong.imlib.model.MessageContent;

/**
 * Created by 24020 on 2017/4/6.
 */
@MessageTag(value = MsgType.SMALL_VIDEO, flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class VideoMsg extends MessageContent {
    private String cover;
    private String extra;
    private String idx;
    private String rate;
    private String status;
    private String url;
    private String pecent;

    public String getPecent() {
        return pecent;
    }

    public void setPecent(String pecent) {
        this.pecent = pecent;
    }

    public VideoMsg() {
    }

    public static final Creator<VideoMsg> CREATOR = new Creator() {
        public VideoMsg createFromParcel(Parcel source) {
            return new VideoMsg(source);
        }

        public VideoMsg[] newArray(int size) {
            return new VideoMsg[size];
        }
    };

    public static VideoMsg obtain(String idx, String url, String rate, String cover, String status, String extra) {
        VideoMsg model = new VideoMsg();
        model.setCover(cover);
        model.setExtra(extra);
        model.setIdx(idx);
        model.setRate(rate);
        model.setStatus(status);
        model.setUrl(url);
        return model;
    }

    public static VideoMsg obtainim(String idx, String url, String rate, String cover, String status, String extra, String pecent) {
        VideoMsg model = new VideoMsg();
        model.setCover(cover);
        model.setExtra(extra);
        model.setIdx(idx);
        model.setRate(rate);
        model.setStatus(status);
        model.setUrl(url);
        model.setPecent(pecent);
        return model;
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();


        try {
            if (!TextUtils.isEmpty(this.getIdx())) {
                jsonObj.put("idx", this.getIdx());
            }
            if (!TextUtils.isEmpty(this.getUrl())) {
                jsonObj.put("url", this.getUrl());
            }
            if (!TextUtils.isEmpty(this.getRate())) {
                jsonObj.put("rate", this.getRate());
            }
            if (!TextUtils.isEmpty(this.getCover())) {
                jsonObj.put("cover", this.getCover());
            }
            if (!TextUtils.isEmpty(this.getStatus())) {
                jsonObj.put("status", this.getStatus());
            }
            if (!TextUtils.isEmpty(this.getExtra())) {
                jsonObj.put("extra", this.getExtra());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    //    idx;
//    url;
//    rate;
//    cover;
//    status;
//    extra;
    public VideoMsg(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }
        try {
            JSONObject e = new JSONObject(jsonStr);
            LogUtil.i("chatMsgxxx", jsonStr);
            if (e.has("idx")) {
                this.setIdx(e.optString("idx"));
            }
            if (e.has("url")) {
                this.setUrl(e.optString("url"));
            }
            if (e.has("rate")) {
                this.setRate(e.optString("rate"));
            }
            if (e.has("cover")) {
                this.setCover(e.optString("cover"));
            }
            if (e.has("status")) {
                this.setStatus(e.optString("status"));
            }
            if (e.has("extra")) {
                this.setExtra(e.optString("extra"));
            }
        } catch (JSONException e1) {
            RLog.e("Smallvmsg", "JSONException " + e1.getMessage());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, this.idx);
        ParcelUtils.writeToParcel(dest, this.url);
        ParcelUtils.writeToParcel(dest, this.rate);
        ParcelUtils.writeToParcel(dest, this.cover);
        ParcelUtils.writeToParcel(dest, this.status);
        ParcelUtils.writeToParcel(dest, this.extra);


    }

    public VideoMsg(Parcel in) {
        this.setIdx(ParcelUtils.readFromParcel(in));
        this.setUrl(ParcelUtils.readFromParcel(in));
        this.setRate(ParcelUtils.readFromParcel(in));
        this.setCover(ParcelUtils.readFromParcel(in));
        this.setStatus(ParcelUtils.readFromParcel(in));
        this.setExtra(ParcelUtils.readFromParcel(in));
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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
}
