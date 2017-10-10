package com.youzi.okredoo.model;

/**
 * Created by hjw on 17/3/28.
 */

public enum VideoChatStatus {
    UNCHAT,              //未连麦
    INVITE_FOR_RES,      //邀请连麦成功等待对方响应
    RECEIVED_INVITE,     //收到邀请等待回复状态
    TRY_MIX,            //开始连麦等待混流成功
    MIX_SUCC,           //混流成功
    CLOSE_FOR_REQ;      //结束连麦请求
}
