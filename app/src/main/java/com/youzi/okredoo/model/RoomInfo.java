package com.youzi.okredoo.model;

/**
 * Created by houjingwei on 2016/6/11.
 */
public class RoomInfo {

    private String userId;
    private String roomName;
    private String roomToken;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomToken() {
        return roomToken;
    }

    public void setRoomToken(String roomToken) {
        this.roomToken = roomToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
