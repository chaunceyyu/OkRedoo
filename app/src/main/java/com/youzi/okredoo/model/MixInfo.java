package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by hjw on 2017/4/2.
 */

public class MixInfo implements Serializable {

    private String cid;
    private String invistor;
    private String mix;
    private String pushUrl;
    private String source;


    //连麦相关
    private String micPush;
    private String micPlay;
    private String mainPlay;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getInvistor() {
        return invistor;
    }

    public void setInvistor(String invistor) {
        this.invistor = invistor;
    }

    public String getMix() {
        return mix;
    }

    public void setMix(String mix) {
        this.mix = mix;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMicPush() {
        return micPush;
    }

    public void setMicPush(String micPush) {
        this.micPush = micPush;
    }

    public String getMicPlay() {
        return micPlay;
    }

    public void setMicPlay(String micPlay) {
        this.micPlay = micPlay;
    }

    public String getMainPlay() {
        return mainPlay;
    }

    public void setMainPlay(String mainPlay) {
        this.mainPlay = mainPlay;
    }
}
