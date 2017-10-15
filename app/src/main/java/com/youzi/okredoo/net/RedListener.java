package com.youzi.okredoo.net;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.youzi.okredoo.App;
import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.data.UserList;
import com.youzi.okredoo.model.GetRedPack;
import com.youzi.okredoo.model.Live;
import com.youzi.okredoo.model.RedPackInfo;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.model.response.Chairesponse;
import com.youzi.okredoo.msgtype.RedPackMsg;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;


public class RedListener {

    private static final String TAG = "OkRedoo";

    private static final int LOOP_MESSAGE_ENTER = 1;
//    private static final int LOOP_MESSAGE_EXIT = 2;

    private static final String RECEIVE_MSG = "receive_message_data";


    private Executor mRedExecutor = Executors.newCachedThreadPool();
    private Executor mLiveExecutor = Executors.newSingleThreadExecutor();

    private static RedListener sRedListener;

    private Context mContext;
    private List<Live> mLiveList = new ArrayList<>();

    private List<LiveRoom> mLiveRoomList = new ArrayList<>();

    public int mDelayMin = 900;
    public int mDelayMax = 1400;
    private boolean isEnable = false;

    public void setMaxDelay(int ms) {
        mDelayMax = ms;
    }

    public void setMinDelay(int ms) {
        mDelayMin = ms;
    }

    public void enabled() {
        isEnable = true;
        mHandler.removeMessages(LOOP_MESSAGE_ENTER);
        start();
    }

    public void disabled() {
        isEnable = false;
        mHandler.removeMessages(LOOP_MESSAGE_ENTER);
        exitAll();
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case LOOP_MESSAGE_ENTER:
                    exitAll();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadLiveList();
                            mHandler.sendEmptyMessageDelayed(LOOP_MESSAGE_ENTER, 3 * 60 * 1000);
                        }
                    }, 2000);
                    break;
//                case LOOP_MESSAGE_EXIT:
//                    exitAll();
//                    break;

            }
        }
    };

    private void start() {
//        mHandler.removeMessages(LOOP_MESSAGE_EXIT);
        mHandler.sendEmptyMessage(LOOP_MESSAGE_ENTER);
    }

    public static void init(Context context) {
        if (sRedListener == null) {
            sRedListener = new RedListener(context);
        }
    }

    public static RedListener get() {
        if (sRedListener != null) {
            return sRedListener;
        }

        throw new NullPointerException();
    }

    private RedListener(Context c) {
        mContext = c;
    }

    private void loadLiveList() {
        mLiveList.clear();
        final Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("pageSize", "200");


        RequestUtils.sendPostRequest(Api.HOME_LIVING_LIST, params, new ResponseCallBack<List<Live>>() {
            @Override
            public void onSuccess(List<Live> data) {
                mLiveList.addAll(data);
                Log.w(TAG, "LoadLiveList:" + mLiveList.size());
                Log.w(TAG, "Delay config:" + mDelayMin + "-" + mDelayMax + "ms");

                postMessageTxt("LoadLiveList:" + mLiveList.size());
                postMessageTxt("Delay config:" + mDelayMin + "-" + mDelayMax + "ms");

                for (int i = 0; i < mLiveList.size(); i++) {
                    final LiveRoom liveRoom = new LiveRoom(mLiveList.get(i));
                    mLiveRoomList.add(liveRoom);
                    mLiveExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            liveRoom.enter();
                        }
                    });
                }
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
            }
        });
    }

    private void exitAll() {
//        mHandler.removeMessages(LOOP_MESSAGE_EXIT);
        mLiveExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mLiveList.size(); i++) {
                    final LiveRoom liveRoom = new LiveRoom(mLiveList.get(i));
                    liveRoom.exit();
                }
//                if (isEnable()) {
//                    mHandler.sendEmptyMessageDelayed(LOOP_MESSAGE_ENTER, 3000);
//                }
            }
        });
    }

    public void receiveMessage(final Message message) {
        if (isEnable()) {
            dealRedMessage(message);
//            notifyLiveRoomMessage(message);
        }
    }

    private void dealRedMessage(final Message message) {
        if (message.getConversationType() == Conversation.ConversationType.PRIVATE || message.getConversationType() == Conversation
                .ConversationType.GROUP || message.getConversationType() == Conversation.ConversationType.CHATROOM) {

            if (message.getContent() instanceof RedPackMsg) {

                final RedPackMsg redMessage = (RedPackMsg) message.getContent();

                Log.w(TAG, "Coming Red Pack -> rpid:" + redMessage.getRpid() + "|senderName:" + redMessage.getSenderName() + "|senderId:" +
                        redMessage
                                .getSenderId() + "|type:" + message.getConversationType().getName() + "|targetId:" + message
                        .getTargetId());

                postMessageTxt("Coming Red Pack -> rpid:" + redMessage.getRpid() + "|senderName:" + redMessage.getSenderName() + "|senderId:" +
                        redMessage
                                .getSenderId() + "|type:" + message.getConversationType().getName() + "|targetId:" + message
                        .getTargetId());

                UserList users = App.getOnlineUserList();
                for (int i = 0; i < users.size(); i++) {
                    final User user = users.get(i);
                    mRedExecutor.execute(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Random r = new Random();
                                int delayTime = r.nextInt(mDelayMax - mDelayMin) + mDelayMin;
                                Log.w(TAG, "delayTime:" + delayTime);
                                postMessageTxt("delayTime:" + delayTime);

                                Thread.sleep(delayTime);
//                            Thread.sleep(mDelayMin);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            redPackROB(redMessage.getRpid(), user, String.valueOf(message.getConversationType().getValue()), message.getTargetId());

                        }
                    });
                }


            } else {
                Log.d(TAG, "NO Red... TargetId:" + message.getTargetId());
                postMessageTxt("NO Red... TargetId:" + message.getTargetId());
            }

        }
    }

    private void notifyLiveRoomMessage(Message message) {
        for (int i = 0; i < mLiveList.size(); i++) {
            LiveRoom liveRoom = mLiveRoomList.get(i);
            liveRoom.onMessageReceive(message);
        }
    }


//    public void bindRed(final RedPackMsg message, final UIMessage uiMessage) {
//        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {
//            return;
//        }
//        CommonHelper.redPackReciveState(message.getRpid(), new ApiCallback<RedPackInfo>() {
//            @Override
//            public void onSuccess(RedPackInfo data) {
//                Log.d(TAG, "redPackReciveState onSuccess:" + new Gson().toJson(data));
//                //我领取了红包
//                if ("1".equals(data.getMyRecevie())) {
//                    Log.d(TAG, "我已领取了红包");
//                } else {
//                    Log.d(TAG, "我去抢红包");
//                    redPackROB(message.getRpid(), String.valueOf(uiMessage.getConversationType().getValue()), uiMessage.getTargetId());
//                }
//            }
//
//            @Override
//            public void onFailure(ServiceException e) {
//                super.onFailure(e);
//                Log.d(TAG, "redPackReciveState onFailure:" + new Gson().toJson(e));
//            }
//        });
//
//    }


    public void redPackROB(final String rpid, final User user, String type, String targetId) {
        Map<String, String> params = new HashMap<>();
        params.put("rpid", rpid);
        params.put("conversationType", type);
        params.put("pass", "");
        params.put("targetid", targetId);

        RequestUtils.sendPostRequest(Api.REDPACK_ROB, user.getUid(), user.getToken(), params, new ResponseCallBack<Chairesponse>() {
            @Override
            public void onSuccess(Chairesponse data) {
                super.onSuccess(data);
                Log.w(TAG, "REDPACK_ROB onSuccess:" + new Gson().toJson(data));
                postMessageTxt("REDPACK_ROB onSuccess:" + new Gson().toJson(data));
                getRedInfo(rpid, user);

                saveGetRedPack(new GetRedPack(user.getUid(), rpid, Integer.valueOf(data.count), System.currentTimeMillis()));

            }

            private void saveGetRedPack(GetRedPack getRedPack) {
                DBManager.getInstance().saveUserGetRedPack(getRedPack);
            }

            private void getRedInfo(String rpid, User user) {
                redPackReciveState(rpid, user, new ApiCallback<RedPackInfo>() {
                    @Override
                    public void onSuccess(RedPackInfo data) {
                        Log.w(TAG, "redPackReciveState onSuccess:" + new Gson().toJson(data));
                        postMessageTxt("redPackReciveState onSuccess:" + new Gson().toJson(data));
                        DBManager.getInstance().saveRedPack(data);
                    }

                    @Override
                    public void onFailure(ServiceException e) {
                        super.onFailure(e);
                        Log.e(TAG, "redPackReciveState onFailure:" + new Gson().toJson(e));
                        postMessageTxt("redPackReciveState onFailure:" + new Gson().toJson(e));
                    }
                });
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                Log.e(TAG, "REDPACK_ROB onFailure:" + new Gson().toJson(e));
                postMessageTxt("REDPACK_ROB onFailure:" + new Gson().toJson(e));
            }
        });
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setDelayClose(int min) {
        mHandler.removeCallbacks(closeRunnable);
        if (min != 0) {
            mHandler.postDelayed(closeRunnable, min * 60 * 1000);
        }
    }

    private Runnable closeRunnable = new Runnable() {
        @Override
        public void run() {
            System.exit(0);
        }
    };


    public class LiveRoom {

        private Live mLive;

        public LiveRoom(Live live) {
            mLive = live;
        }


        public void onMessageReceive(Message message) {
            if (MsgType.LIVE_EXIT.equals(message.getObjectName())) {
                //直播关闭消息
                exit();
            }
        }

        public void enter() {
//            Map<String, String> params = new HashMap<>();
//            params.put("chatroom", mLive.getLid());
//            params.put("nickName", App.getLoginUser().getNickName());
//            params.put("grade", App.getLoginUser().getGrade());
//            params.put("photo", App.getLoginUser().getPhoto());
//            params.put("lid", mLive.getLid());
//            RequestUtils.sendPostRequest(Api.INTO_ROOM, params, new ResponseCallBack<String>() {
//                @Override
//                public void onSuccess(String str) {
//                    super.onSuccess(str);
////                    Log.d(TAG, "INTO_ROOM onSuccess Lid:" + mLive.getLid() + "|NickName:" + mLive.getNickName() + "|City:" + mLive.getCity());
//                }
//
//                @Override
//                public void onFailure(ServiceException e) {
//                    super.onFailure(e);
////                    Log.d(TAG, "INTO_ROOM onFailure:" + e.getMsg());
//                }
//            }, String.class);
            checkRongyunConnectionAndJoinRoom(mLive);
        }

        private void exit() {
            RongIMClient.getInstance().quitChatRoom(mLive.getLid(), new RongIMClient.OperationCallback() {

                @Override
                public void onSuccess() {
                    Log.d(TAG, "Exit Room:" + mLive.getLid());
                    postMessageTxt("Exit Room:" + mLive.getLid());
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e(TAG, "Exit Room onError:" + mLive.getLid() + " | errorCode:" + errorCode.getMessage());
                }
            });
//            exitLiving(mLive.getLid());
        }

//        private void exitLiving(String lid) {
//            Map<String, String> para = new HashMap<>();
//            para.put("lid", lid);
//            RequestUtils.sendPostRequest(Api.LIVING_PLAYER_EXIT, para, new ResponseCallBack<Other>() {
//
//                @Override
//                public void onSuccess(Other data) {
//                    super.onSuccess(data);
//                }
//
//                @Override
//                public void onFailure(ServiceException e) {
//                }
//            });
//        }

        private void checkRongyunConnectionAndJoinRoom(Live live) {
            int flag = RongIMClient.getInstance().getCurrentConnectionStatus().getValue();
            if (flag == 0 || flag == 1) {
                joinRoom(live);
            } else {
                reJoinRoom(live);
            }
        }


        private void reJoinRoom(final Live live) {
            RongIMClient.getInstance().quitChatRoom(live.getLid(), new RongIMClient.OperationCallback() {
                @Override
                public void onSuccess() {
                    joinRoom(live);
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e(TAG, "Exit Room onError:" + live.getLid() + " | errorCode:" + errorCode.getMessage());
                }
            });
        }

        private void joinRoom(Live live) {
            RongIMClient.getInstance().joinChatRoom(live.getLid(), -1, new RongIMClient.OperationCallback() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "Enter Room:" + mLive.getLid());
                    postMessageTxt("Enter Room:" + mLive.getLid());
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e(TAG, "Enter Room onError:" + mLive.getLid() + " | errorCode:" + errorCode.getMessage());
                    postMessageTxt("Enter Room onError:" + mLive.getLid() + " | errorCode:" + errorCode.getMessage());
                }
            });
        }
    }

    public void redPackReciveState(String rpid, User user, final ApiCallback<RedPackInfo> apiCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("rpid", rpid);
        RequestUtils.sendPostRequest(Api.RED_PACK_INFO, user.getUid(), user.getToken(), params, new ResponseCallBack<RedPackInfo>() {
            @Override
            public void onSuccess(RedPackInfo data) {
                super.onSuccess(data);
                apiCallback.onSuccess(data);
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                apiCallback.onFailure(e);
            }
        });
    }

    private void postMessageTxt(String msg) {
        EventBus.getDefault().post(msg, RECEIVE_MSG);
    }

}
