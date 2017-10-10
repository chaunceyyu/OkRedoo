package com.youzi.okredoo.model;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjw on 17/1/23.
 */
public class LiveAttention_my implements Serializable {

//    bottom1	    直播为空，图片视频为点赞数	string
//    bottom2	    直播为空，图片视频为粮票数评论	string
//    bottom3	    直播为空，图片视频为粮票数	string
//    content	    直播为直播标题，图片视频为文字内容	string
//    grade	        等级	string
//    nickName	    昵称	string
//    photo	        头像	string
//    topRight	    直播为在线人数，视频图片为点击数量	string
//    topSubTitle	直播为开播时间，视频图片为发表时间	string
//    topTitle  	昵称	string
//    type	        类型，0直播1图片2视频	string
//    url

     private String bottom1	 ;
     private String bottom2	 ;
     private String bottom3	 ;
     private String content	 ;
     private String grade	     ;
     private String nickName	 ;
     private String photo	     ;
     private String topRight	 ;
     private String topSubTitle;
     private String topTitle  ;
     private String type	     ;
     private String url;
    private List<DynamicImg> images;
    private String did;
    private String cover;

    private String vedio_time;   //视频时长
    private String isRecord;    //是否直播回放

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getBottom1() {
        return bottom1;
    }

    public void setBottom1(String bottom1) {
        this.bottom1 = bottom1;
    }

    public String getBottom2() {
        return bottom2;
    }

    public void setBottom2(String bottom2) {
        this.bottom2 = bottom2;
    }

    public String getBottom3() {
        return bottom3;
    }

    public void setBottom3(String bottom3) {
        this.bottom3 = bottom3;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTopRight() {
        return topRight;
    }

    public void setTopRight(String topRight) {
        this.topRight = topRight;
    }

    public String getTopSubTitle() {
        return topSubTitle;
    }

    public void setTopSubTitle(String topSubTitle) {
        this.topSubTitle = topSubTitle;
    }

    public String getTopTitle() {
        return topTitle;
    }

    public void setTopTitle(String topTitle) {
        this.topTitle = topTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<DynamicImg> getImages() {
        return images;
    }

    public void setImages(List<DynamicImg> images) {
        this.images = images;
    }

    public String getVedio_time() {
        return TextUtils.isEmpty(vedio_time)?"0":vedio_time;
    }

    public void setVedio_time(String vedio_time) {
        this.vedio_time = vedio_time;
    }

    public boolean isLive(){
        return "0".equals(type);
    }

    public boolean isPicture(){
        return "1".equals(type);
    }

    public boolean isVideo(){
        return "2".equals(type);
    }

    public String getIsRecord() {
        return isRecord;
    }

    public void setIsRecord(String isRecord) {
        this.isRecord = isRecord;
    }

    public boolean isRecord() {
        return "1".equals(isRecord);
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public static LiveAttention_my obtainFromLive(Live l){
        LiveAttention_my t=new LiveAttention_my();
        t.setType("0");
        t.setDid(l.getLid());
        t.setPhoto(l.getPhoto());
        t.setTopTitle(l.getNickName());
        t.setTopSubTitle(l.getCity());
        t.setTopRight(l.getOnline());
        t.setCover(l.getCover());
        ArrayList<DynamicImg> images=new ArrayList<>();
        images.add(new DynamicImg(l.getPhoto()));
        t.setImages(images);
        return t;
    }
}
