package com.youzi.okredoo.model.response;

/**
 * Created by 24020 on 2017/2/16.
 */
public class GroupInfoResponse {
//    cover	        群封面	string
//    creatorTime	创建时间
//    gid	        群id	string
//    joinType
//    name	        名称	string
//    nickName	    群主名称	string
//    photo	            群主头像	string
//    uid	         群主id	string
//    users
    private String cover	     ;
    private String creatorTime;
    private String gid	     ;
    private String joinType;
    private String name	     ;
    private String nickName	 ;
    private String photo	     ;
    private String uid	     ;
    private String users;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(String creatorTime) {
        this.creatorTime = creatorTime;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }
}
