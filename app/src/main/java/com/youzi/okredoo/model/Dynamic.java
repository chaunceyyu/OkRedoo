package com.youzi.okredoo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by houjingwei on 2017/1/28.
 */
public class Dynamic implements Serializable {

    public static final int LOCAL_TYPE_UPLOAD_VIDEO = 1;

    public static final int TYPE_VIDEO = 2;
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_FORWORD = 0;

    private String commentCount;
    private String content;
    private String date;
    private String did;
    private String nickName;
    private String photo;
    private String praiseCount;         //点赞数
    private String isPraise;            //是否点过赞
    private String type;                //0转发  1图片  2视频
    private String hots;                //打赏数
    private String uid;
    private String videoCover;
    private String videoURL;
//    private List<Comment> comments;
    private List<DynamicImg> images;
    private List<Praise> praises;       //打赏
    private List<Praise> praisesName;   //点赞
    private String lid;
    private String viewStatus;
    private String sourceContent;       //转发title
    private String sourceCover;         //转发image
    private String sourceUrl;           //转发url


    //用于计算的属性
    private String lastDate = "";
    private String month = "";
    private String day = "";
    private boolean marginTop = false;
    private boolean isAdd = false;

    //用于判断显示上传中的视频
    private int localType = 0;
    //上传进度
    private int progress;

    private boolean isMore;//展开更多内容
    private String sex;
    private int grade;
    private boolean isEnable;

    @SerializedName("is_record")
    private String isRecord;

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isMarginTop() {
        return marginTop;
    }

    public void setMarginTop(boolean marginTop) {
        this.marginTop = marginTop;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
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

    public String getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(String praiseCount) {
        this.praiseCount = praiseCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVideoCover() {
        return videoCover;
    }

    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

//    public List<Comment> getComments() {
//        return comments == null ? new ArrayList<Comment>() : comments;
//    }
//
//    public void setComments(List<Comment> comments) {
//        this.comments = comments;
//    }

    public List<DynamicImg> getImages() {
        return images;
    }

    public void setImages(List<DynamicImg> images) {
        this.images = images;
    }

    public List<Praise> getPraises() {
        return praises == null ? new ArrayList<Praise>() : praises;
    }

    public void setPraises(List<Praise> praises) {
        this.praises = praises;
    }

    /**
     * @see #praisesName
     */
    public List<Praise> getPraisesName() {
        return praisesName == null ? new ArrayList<Praise>() : praisesName;
    }

    /**
     * @see #praisesName
     */
    public void setPraisesName(List<Praise> praisesName) {
        this.praisesName = praisesName;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }


    public String getHots() {
        return hots;
    }

    public void setHots(String hots) {
        this.hots = hots;
    }

    public String getIsPraise() {
        return isPraise;
    }

    public boolean isPraise() {
        return "1".equals(getIsPraise());
    }

    public void setIsPraise(String isPraise) {
        this.isPraise = isPraise;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus;
    }

    public String getSourceContent() {
        return sourceContent;
    }

    public void setSourceContent(String sourceContent) {
        this.sourceContent = sourceContent;
    }

    public String getSourceCover() {
        return sourceCover;
    }

    public void setSourceCover(String sourceCover) {
        this.sourceCover = sourceCover;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public int getLocalType() {
        return localType;
    }

    public void setLocalType(int localType) {
        this.localType = localType;
    }

    public int getProgress() {
        return progress;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isRecord() {
        return "1".equals(isRecord);
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public void update(Dynamic dynamic) {
        setDate(dynamic.getDate());
        setContent(dynamic.getContent());
        setVideoURL(dynamic.getVideoURL());
        setVideoCover(dynamic.getVideoCover());
        setViewStatus(dynamic.getViewStatus());
        setLocalType(0);
        setType(dynamic.getType());
        setUid(dynamic.getUid());
        setDid(dynamic.getDid());
    }
}
