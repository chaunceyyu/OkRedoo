package com.youzi.okredoo.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class RedPackInfo implements Serializable {
//    costTime	    销毁时间	string
//    count	        总数	string
//    info	        留言	string
//    myRecevie	    我是否领取0未，1已	string
//    myReciveCount	我领取的数量	string
//    receive	    领取数	string
//    rpid	        红包id	string
//    sendName		string
//    sendPhoto		string
//    sendUid
//    coin          红包总金额


    private String costTime;
    private String count;
    private String info;
    private String myRecevie;
    private String myReciveCount;
    private String receive;
    @Id
    private String rpid;
    private String sendName;
    private String sendPhoto;
    private String sendUid;
    private String type;
    private String coin;


    @Generated(hash = 1554347351)
    public RedPackInfo(String costTime, String count, String info,
            String myRecevie, String myReciveCount, String receive, String rpid,
            String sendName, String sendPhoto, String sendUid, String type,
            String coin) {
        this.costTime = costTime;
        this.count = count;
        this.info = info;
        this.myRecevie = myRecevie;
        this.myReciveCount = myReciveCount;
        this.receive = receive;
        this.rpid = rpid;
        this.sendName = sendName;
        this.sendPhoto = sendPhoto;
        this.sendUid = sendUid;
        this.type = type;
        this.coin = coin;
    }

    @Generated(hash = 639977368)
    public RedPackInfo() {
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSendPhoto() {
        return sendPhoto;
    }

    public void setSendPhoto(String sendPhoto) {
        this.sendPhoto = sendPhoto;
    }

    public String getSendUid() {
        return sendUid;
    }

    public void setSendUid(String sendUid) {
        this.sendUid = sendUid;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    public String getMyRecevie() {
        return myRecevie;
    }

    public void setMyRecevie(String myRecevie) {
        this.myRecevie = myRecevie;
    }

    public String getRpid() {
        return rpid;
    }

    public void setRpid(String rpid) {
        this.rpid = rpid;
    }

    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMyReciveCount() {
        return myReciveCount;
    }

    public void setMyReciveCount(String myReciveCount) {
        this.myReciveCount = myReciveCount;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }
}
