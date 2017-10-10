package com.youzi.okredoo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.data.UserList;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.msgtype.BarrageMsg;
import com.youzi.okredoo.msgtype.CardMsg;
import com.youzi.okredoo.msgtype.CycMsg;
import com.youzi.okredoo.msgtype.EmojMsg;
import com.youzi.okredoo.msgtype.EnterMsg;
import com.youzi.okredoo.msgtype.ExitMsg;
import com.youzi.okredoo.msgtype.GiftMsg;
import com.youzi.okredoo.msgtype.LiveBehaviorMsg;
import com.youzi.okredoo.msgtype.LiveNotifyMsg;
import com.youzi.okredoo.msgtype.LiveOnlineMsg;
import com.youzi.okredoo.msgtype.LiveTopFansMsg;
import com.youzi.okredoo.msgtype.MixCloseMsg;
import com.youzi.okredoo.msgtype.MixFeedMsg;
import com.youzi.okredoo.msgtype.MixReqMsg;
import com.youzi.okredoo.msgtype.MixStartMsg;
import com.youzi.okredoo.msgtype.PushGoodMsg;
import com.youzi.okredoo.msgtype.PushMsg;
import com.youzi.okredoo.msgtype.RedPackMsg;
import com.youzi.okredoo.msgtype.RoomAdminMsg;
import com.youzi.okredoo.msgtype.RoomTxtMsg;
import com.youzi.okredoo.msgtype.ShareMsg;
import com.youzi.okredoo.msgtype.SlicentMessage;
import com.youzi.okredoo.msgtype.ToBuyMsg;
import com.youzi.okredoo.msgtype.VerifyMsg;
import com.youzi.okredoo.msgtype.VideoChatMsg;
import com.youzi.okredoo.msgtype.VideoMsg;
import com.youzi.okredoo.net.RedListener;
import com.youzi.okredoo.util.AppUtil;

import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.rong.imlib.AnnotationNotFoundException;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

/**
 * Created by zhangjiajie on 2017/10/8.
 */

public class App extends Application {

    private static Context sContext;

    //    public static UserList sUserList = new UserList();
    public static UserList sOnlineUserList = new UserList();

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        Config.deviceId = AppUtil.getIMEI(this);
        DBManager.init(this);
        x.Ext.init(this);

        RongIMClient.init(this, "n19jmcy5ndgn9");

        RedListener.init(this);

        RongIMClient.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                RedListener.get().receiveMessage(message);
                return true;
            }
        });

        registerMessageType();

        loadOnlineUserData();
    }

    public static UserList getUserList() {
        List<User> userList = DBManager.getInstance().getUserDao().loadAll();
        if (userList != null) {
            UserList sUserList = new UserList();
            sUserList.clear();
            sUserList.addAll(userList);
            return sUserList;
        }
        return null;
    }

    public static void loadOnlineUserData() {
        String json = getSP().getString("onlineUserList", null);
        Type type = new TypeToken<ArrayList<User>>() {
        }.getType();
        List<User> userList = new Gson().fromJson(json, type);
        if (userList != null) {
            sOnlineUserList.addAll(userList);
        }
    }

    public static SharedPreferences getSP() {
        return getContext().getSharedPreferences(getContext().getPackageName(), MODE_PRIVATE);
    }

    public static Context getContext() {
        return sContext;
    }

    private void registerMessageType() {
        try {
            RongIMClient.registerMessageType(EnterMsg.class);
            RongIMClient.registerMessageType(BarrageMsg.class);
            RongIMClient.registerMessageType(ExitMsg.class);
            RongIMClient.registerMessageType(GiftMsg.class);
            RongIMClient.registerMessageType(RoomTxtMsg.class);
            RongIMClient.registerMessageType(ToBuyMsg.class);
            RongIMClient.registerMessageType(RedPackMsg.class);
            RongIMClient.registerMessageType(VideoMsg.class);
            RongIMClient.registerMessageType(CycMsg.class);
            RongIMClient.registerMessageType(EmojMsg.class);

            //连麦相关
            //连麦申请
            RongIMClient.registerMessageType(MixReqMsg.class);
            //连麦反馈
            RongIMClient.registerMessageType(MixFeedMsg.class);
            //观众同意直播
            RongIMClient.registerMessageType(MixStartMsg.class);
            //连麦关闭
            RongIMClient.registerMessageType(MixCloseMsg.class);

            //一对一直播
            RongIMClient.registerMessageType(VideoChatMsg.class);

            //禁言或解禁，踢出
            RongIMClient.registerMessageType(SlicentMessage.class);

            //推送商品
            RongIMClient.registerMessageType(PushGoodMsg.class);

            //后台推送的主播违规操作消息
            RongIMClient.registerMessageType(RoomAdminMsg.class);

            //系統推送消息
            RongIMClient.registerMessageType(PushMsg.class);

            //直播提醒消息
            RongIMClient.registerMessageType(LiveNotifyMsg.class);

            //验证消息
            RongIMClient.registerMessageType(VerifyMsg.class);

            //直播间top user变化
            RongIMClient.registerMessageType(LiveTopFansMsg.class);
            //直播间在线用户变化
            RongIMClient.registerMessageType(LiveOnlineMsg.class);
            //直播间用户行为
            RongIMClient.registerMessageType(LiveBehaviorMsg.class);
            //分享消息
            RongIMClient.registerMessageType(ShareMsg.class);
            //名片消息
            RongIMClient.registerMessageType(CardMsg.class);
        } catch (AnnotationNotFoundException e) {
            e.printStackTrace();
        }
        

    }
}
