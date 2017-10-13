package com.youzi.okredoo.model;

import android.os.Environment;

import com.youzi.okredoo.App;

import java.io.Serializable;

/**
 * Created by hjw on 17/2/18.
 */

public class Gift implements Serializable {


    private String exp;//经验值
    private String gid;//礼物id
    private String icon;//图标
    private String moreSend;//连发0不可以，1可以
    private String name;//名称
    private String price;//价格，20芒果币
    private String reciveAnim;//动画类别 为1的时候才显示右边的动画
    private String sendAnim;//图片路径
    private String type;//增加的人气值
    private String animeUrl;


    private int giftCount;
    private int sequence;
    private String sendUserId;
    private String sendUserName;
    private String sendUserPic;
    private String hitCombo;
    private Long sendGiftTime;

    public Gift(String gid, String name, int giftCont, String icon, String sendUserId,
                String sendUserName, String sendUserPic, Long sendGiftTime) {
        setGid(gid);
        setName(name);
        setGiftCount(giftCont);
        setIcon(icon);
        setSendUserId(sendUserId);
        setSendUserName(sendUserName);
        setSendUserPic(sendUserPic);
        setSendGiftTime(sendGiftTime);
    }

    public Gift() {}


    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMoreSend() {
        return moreSend;
    }

    public void setMoreSend(String moreSend) {
        this.moreSend = moreSend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReciveAnim() {
        return reciveAnim;
    }

    public void setReciveAnim(String reciveAnim) {
        this.reciveAnim = reciveAnim;
    }

    public String getSendAnim() {
        return sendAnim;
    }

    public void setSendAnim(String sendAnim) {
        this.sendAnim = sendAnim;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnimeUrl() {
        return animeUrl;
    }

    public void setAnimeUrl(String animeUrl) {
        this.animeUrl = animeUrl;
    }


    public int getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(int giftCount) {
        this.giftCount = giftCount;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getSendUserPic() {
        return sendUserPic;
    }

    public void setSendUserPic(String sendUserPic) {
        this.sendUserPic = sendUserPic;
    }

    public String getHitCombo() {
        return hitCombo;
    }

    public void setHitCombo(String hitCombo) {
        this.hitCombo = hitCombo;
    }

    public Long getSendGiftTime() {
        return sendGiftTime;
    }

    public void setSendGiftTime(Long sendGiftTime) {
        this.sendGiftTime = sendGiftTime;
    }


    public String getDownloadPath() {
        return getDownloadPath(reciveAnim);
    }

    public static String getDownloadPath(String fileName) {
        //这里的扩展名其实没有意义，经过几次修改，可能是apng/webp/zip  获取 APP Download 文件夹
        return App.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName + ".zip";
    }
}
