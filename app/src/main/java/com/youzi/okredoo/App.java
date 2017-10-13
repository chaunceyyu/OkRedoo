package com.youzi.okredoo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.data.UserList;
import com.youzi.okredoo.gendao.UserDao;
import com.youzi.okredoo.model.Gift;
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
import com.youzi.okredoo.net.Api;
import com.youzi.okredoo.net.ApiCallback;
import com.youzi.okredoo.net.RedListener;
import com.youzi.okredoo.net.RequestUtils;
import com.youzi.okredoo.net.ResponseCallBack;
import com.youzi.okredoo.net.ServiceException;
import com.youzi.okredoo.util.AppUtil;

import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imlib.AnnotationNotFoundException;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

/**
 * Created by zhangjiajie on 2017/10/8.
 */

public class App extends Application {

    private static Context sContext;

    public static int screenWidth, screenHeight;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        widthAndHeight();

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
    }

    private void widthAndHeight() {
        //窗口管理器
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();
    }

    public static UserList getUserList() {
        List<User> userList = DBManager.getInstance().getUserDao().loadAll();
        UserList sUserList = new UserList();
        if (userList != null) {
            sUserList.clear();
            sUserList.addAll(userList);
        }
        return sUserList;
    }

    public static UserList getOnlineUserList() {
        List<User> users = DBManager.getInstance().getUserDao()
                .queryBuilder().where(UserDao.Properties.Online.eq(1)).list();
        UserList sUserList = new UserList();
        if (users != null) {
            sUserList.clear();
            sUserList.addAll(users);
        }
        return sUserList;
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

    //普通礼物列表
    public static List<Gift> giftList = new ArrayList<>();
    //礼物ID与礼物的映射
    public static final Map<String, Gift> giftMap = new HashMap<>();
    public static boolean isGiftLoading = false; //礼物是否加载中

    public static void loadGiftList(final ApiCallback callback) {
        isGiftLoading = true;
        RequestUtils.sendPostRequest(Api.GIFT_LIST, null, new ResponseCallBack<List<Gift>>() {
            @Override
            public void onSuccess(List<Gift> data) {
                isGiftLoading = false;

                if (data == null || data.isEmpty()) {
                    return;
                }

                giftList = data;
                giftMap.clear();
                for (Gift gift : giftList) {
                    giftMap.put(gift.getGid(), gift);
                    if (!TextUtils.isEmpty(gift.getReciveAnim())) {
                        if (!TextUtils.isEmpty(gift.getAnimeUrl())) {
                            String filePath = gift.getDownloadPath();
                            Log.i("ysl", "gift路径>>" + filePath + " | " + gift.getAnimeUrl());
                            if (!new File(filePath).exists()) { //文件不存在
//                                downloadMap.put(gift.getAnimeUrl(), filePath);
                            }
                        } else {
                            gift.setReciveAnim(null);
                        }
                    }
                }
                if (null != callback) {
                    callback.onSuccess(giftMap);
                }

            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                isGiftLoading = false;
            }
        });
    }
}
