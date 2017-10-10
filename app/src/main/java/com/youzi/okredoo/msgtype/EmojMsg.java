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
import io.rong.imlib.model.MessageContent;

/**
 * Created by hjw on 17/2/23.
 */
@MessageTag(value = MsgType.TST_MSG, flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class EmojMsg extends MessageContent {

    protected String content;

    protected String extra;

    public static final Creator<EmojMsg> CREATOR = new Creator() {
        public EmojMsg createFromParcel(Parcel source) {
            return new EmojMsg(source);
        }

        public EmojMsg[] newArray(int size) {
            return new EmojMsg[size];
        }
    };


    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            if (!TextUtils.isEmpty(this.getContent())) {
                jsonObj.put("content", this.getContent());
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

    protected EmojMsg() {
    }

    public static EmojMsg obtain(String content) {
        EmojMsg model = new EmojMsg();
        model.setContent(content);
        return model;
    }

    public static EmojMsg obtain_private(String chatMsg, String count, String icon) {
        EmojMsg model = new EmojMsg();
        return model;
    }

    public EmojMsg(byte[] data) {
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

        ParcelUtils.writeToParcel(dest, this.content);

        ParcelUtils.writeToParcel(dest, this.extra);
    }

    public EmojMsg(Parcel in) {

        this.setContent(ParcelUtils.readFromParcel(in));
        this.setExtra(ParcelUtils.readFromParcel(in));


    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
