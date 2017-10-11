package com.youzi.okredoo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.youzi.okredoo.adapter.ListenerDataListAdapter;
import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.data.UserList;
import com.youzi.okredoo.model.Rongtoken;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.model.User2;
import com.youzi.okredoo.net.Api;
import com.youzi.okredoo.net.RedListener;
import com.youzi.okredoo.net.RequestUtils;
import com.youzi.okredoo.net.ResponseCallBack;
import com.youzi.okredoo.net.ServiceException;
import com.youzi.okredoo.view.MenuDialog;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.rong.imlib.RongIMClient;

/**
 * Created by zhangjiajie on 2017/10/9.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener, AbsListView.OnScrollListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button startOrStopBtn;
    private Button suduBtn;
    private Button autoCloseBtn;
    private Button refreshBtn;
    private Button mlianjieBtn;
    private Button accountBtn;
    private Button clearBtn;
    private Button gundongBtn;
    private Button exitBtn;
    private Button onlineBtn;
    private Button tongjiBtn;
    private Button sendCoinBtn;

    private ProgressBar mProgressBar;
    private ProgressBar mProgressBar2;
    private TextView stateTxt;
    private TextView dataCount;
    private TextView delayTime;
    private TextView speed;
    private TextView coin;

    private ListView mListView;
    private ListenerDataListAdapter mAdapter;
    private ArrayList<String> dataList = new ArrayList<>();

    Handler handler = new Handler();
    private int minMs = 1200;
    private int maxMs = 1800;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        initView();
        refreshUserListInfo();
        bindData();

    }

    private void bindData() {
        coin.setText(String.valueOf(getCoins()));
        handler.postDelayed(timerRunnable, 60 * 1000);

        minMs = App.getSP().getInt("minMs", 1200);
        maxMs = App.getSP().getInt("maxMs", 1800);

        RedListener.get().setMinDelay(minMs);
        RedListener.get().setMaxDelay(maxMs);

        speed.setText(minMs + "-" + maxMs + "ms");
    }

    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            refreshUserListInfo();
            handler.postDelayed(timerRunnable, 60 * 1000);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        startOrStopBtn = (Button) findViewById(R.id.startOrStopBtn);
        suduBtn = (Button) findViewById(R.id.suduBtn);
        refreshBtn = (Button) findViewById(R.id.refreshBtn);
        mlianjieBtn = (Button) findViewById(R.id.lianjieBtn);
        autoCloseBtn = (Button) findViewById(R.id.autoCloseBtn);
        accountBtn = (Button) findViewById(R.id.accountBtn);
        clearBtn = (Button) findViewById(R.id.clearBtn);
        gundongBtn = (Button) findViewById(R.id.gundongBtn);
        exitBtn = (Button) findViewById(R.id.exitBtn);
        onlineBtn = (Button) findViewById(R.id.onlineBtn);
        tongjiBtn = (Button) findViewById(R.id.tongjiBtn);
        sendCoinBtn = (Button) findViewById(R.id.sendCoinBtn);

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setOnScrollListener(this);

        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mProgressBar.setVisibility(View.INVISIBLE);
        mProgressBar2 = (ProgressBar) findViewById(R.id.progress2);
        mProgressBar2.setVisibility(View.INVISIBLE);

        stateTxt = (TextView) findViewById(R.id.state_text);
        stateTxt.setVisibility(View.INVISIBLE);

        dataCount = (TextView) findViewById(R.id.dataCount);
        coin = (TextView) findViewById(R.id.coin);
        speed = (TextView) findViewById(R.id.speed);

        delayTime = (TextView) findViewById(R.id.delayTime);
        delayTime.setVisibility(View.INVISIBLE);

        startOrStopBtn.setOnClickListener(this);
        suduBtn.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);
        mlianjieBtn.setOnClickListener(this);
        autoCloseBtn.setOnClickListener(this);
        accountBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        gundongBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
        onlineBtn.setOnClickListener(this);
        tongjiBtn.setOnClickListener(this);
        sendCoinBtn.setOnClickListener(this);

        mAdapter = new ListenerDataListAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mAdapter.changeDataSet(dataList);
    }

    @Subscriber(tag = "receive_message_data")
    private void messageData(String msg) {
        if (dataList.size() > 20000) {
            clearScreen();
        }
        dataList.add(msg);
        mAdapter.notifyDataSetChanged();
        dataCount.setText(String.valueOf(dataList.size()));
//        if (isActive) {
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    mListView.smoothScrollToPosition(dataList.size() - 1);
//                }
//            });
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (RongIMClient.getInstance().getCurrentConnectionStatus()
                != RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED) {
//            mlianjieBtn.setEnabled(true);
//            mlianjieBtn.setText("连接IM");
            connectIM();
        }

        if (RedListener.get().isEnable()) {
            startOrStopBtn.setText("关闭监听");
        } else {
            startOrStopBtn.setText("启动监听");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        if (view == startOrStopBtn) {
            startOrStop();
        } else if (view == suduBtn) {
            showSpeedMenu();
        } else if (view == autoCloseBtn) {
            showDelayCloseMenu();
        } else if (view == mlianjieBtn) {
            connectIM();
        } else if (view == refreshBtn) {
            refreshUserListInfo();
        } else if (view == accountBtn) {
            startActivity(AccountListActivity.createIntent(mContext));
        } else if (view == clearBtn) {
            clearScreen();
        } else if (view == gundongBtn) {
            mListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        } else if (view == exitBtn) {
            System.exit(0);
        } else if (view == onlineBtn) {
            startActivity(OnlineAccountListActivity.createIntent(mContext));
        } else if (view == tongjiBtn) {
            startActivity(StatisticsActivity.createIntent(mContext));
        } else if (view == sendCoinBtn) {
            startActivity(SendCoinActivity.createIntent(mContext));
        }
    }

    private void startOrStop() {
        if (RedListener.get().isEnable()) {
            RedListener.get().disabled();
            mProgressBar.setVisibility(View.INVISIBLE);
            stateTxt.setVisibility(View.INVISIBLE);
            startOrStopBtn.setText("启动监听");

        } else {
            if (App.getOnlineUserList().isEmpty()) {
                showToast("无在线账号");
                return;
            }
            RedListener.get().enabled();
            mProgressBar.setVisibility(View.VISIBLE);
            stateTxt.setVisibility(View.VISIBLE);
            stateTxt.setText("正在运行...");
            startOrStopBtn.setText("关闭监听");

        }
    }

    private void clearScreen() {
        dataList.clear();
        mAdapter.notifyDataSetChanged();
        dataCount.setText(String.valueOf(dataList.size()));
    }

    private void refreshUserListInfo() {
        UserList users = App.getOnlineUserList();
        if (users.isEmpty()) {
            showToast("无在线账号数据");
            return;
        }

        mProgressBar2.setVisibility(View.VISIBLE);

        for (int i = 0; i < users.size(); i++) {
            final User user = users.get(i);
            Map<String, String> params = new HashMap<>();
            RequestUtils.sendPostRequest(Api.GETUSERINFO, user.getUid(), user.getToken(), params, new ResponseCallBack<User2>() {
                @Override
                public void onSuccess(User2 u) {
                    user.setCoins(u.getCoins());
                    user.setNickName(u.getNickName());
                    DBManager.getInstance().updateUser(user);

                    handler.removeCallbacks(updateCoinsRunnable);
                    handler.postDelayed(updateCoinsRunnable, 3000);
                }

                @Override
                public void onFailure(ServiceException e) {
                    super.onFailure(e);
                    showToast(e.getMsg());
                }
            });
        }
    }

    Runnable updateCoinsRunnable = new Runnable() {
        @Override
        public void run() {
            mProgressBar2.setVisibility(View.INVISIBLE);
            coin.setText(String.valueOf(getCoins()));
        }
    };


    private void connectIM() {
        UserList users = App.getUserList();
        if (users.isEmpty()) {
            showToast("先添加账号才能连接IM");
            return;
        }

        showProgress("连接中", "正在连接服务器");

        rongToken(users.get(0), new ResponseCallBack<Rongtoken>() {
            @Override
            public void onSuccess(Rongtoken data) {
                super.onSuccess(data);
                connetRongIM(data.getToken());
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                closeProgress();
            }
        });
    }


    /**
     * 获取融云token
     * nickName		string
     * photo
     */

    public static void rongToken(User user, final ResponseCallBack<Rongtoken> responseCallBack) {
        Map<String, String> para1 = new HashMap<>();
        if (TextUtils.isEmpty(user.getPhoto())) {
            user.setPhoto("");
        }
        para1.put("nickName", user.getNickName());
        para1.put("photo", user.getPhoto());
        para1.put("uid", user.getUid());
        para1.put("token", user.getToken());
        RequestUtils.sendPostRequest(Api.RONGTOKEN, para1, new ResponseCallBack<Rongtoken>() {
            @Override
            public void onSuccess(Rongtoken data) {
                super.onSuccess(data);
                responseCallBack.onSuccess(data);
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                responseCallBack.onFailure(e);
            }

        });

    }

    public void connetRongIM(String token) {
        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {    //这个方法不在uiThread回调
                closeProgress();
                showToast("onTokenIncorrect");
            }

            @Override
            public void onSuccess(String s) {
                closeProgress();
                mlianjieBtn.setEnabled(false);
                mlianjieBtn.setText("已连接");
                showToast("已连接");
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                closeProgress();
                showToast(errorCode.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    public void showDelayCloseMenu() {
        String[] textArray = new String[]{"30min", "60min", "90min", "120min", "180min", "210min", "240min", "Cancel"};

        new MenuDialog(mContext).setMenuText(textArray)
                .setOnItemClickListener(new MenuDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(MenuDialog dialog, int index) {
                        int min = 0;
                        switch (index) {
                            case 0:
                                min = 30;
                                break;
                            case 1:
                                min = 60;
                                break;
                            case 2:
                                min = 90;
                                break;
                            case 3:
                                min = 120;
                                break;
                            case 4:
                                min = 180;
                                break;
                            case 5:
                                min = 210;
                                break;
                            case 6:
                                min = 240;
                                break;
                        }
                        RedListener.get().setDelayClose(min);
                        if (min != 0) {
                            delayTime.setVisibility(View.VISIBLE);
                            long time = System.currentTimeMillis() + min * 60 * 1000;
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                            delayTime.setText(simpleDateFormat.format(new Date(time)));
                            showToast("After " + min + " min to close");
                        } else {
                            delayTime.setVisibility(View.INVISIBLE);
                            showToast("Cancel close");
                        }

                    }
                }).show();
    }

    public void showSpeedMenu() {
        String[] textArray = new String[]{"600-1200ms", "800-1400ms", "1000-1600ms", "1200-1800ms(Default)", "1400-2000ms", "1600-2200ms",
                "1800-2400ms", "2000-2600ms", "2200-2800ms"};

        new MenuDialog(mContext).setMenuText(textArray)
                .setOnItemClickListener(new MenuDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(MenuDialog dialog, int index) {
                        switch (index) {
                            case 0:
                                minMs = 600;
                                maxMs = 1200;

                                break;
                            case 1:
                                minMs = 800;
                                maxMs = 1400;
                                break;
                            case 2:
                                minMs = 1000;
                                maxMs = 1600;
                                break;
                            case 3:
                                minMs = 1200;
                                maxMs = 1800;
                                break;
                            case 4:
                                minMs = 1400;
                                maxMs = 2000;
                                break;
                            case 5:
                                minMs = 1600;
                                maxMs = 2200;
                                break;
                            case 6:
                                minMs = 1800;
                                maxMs = 2400;
                                break;
                            case 7:
                                minMs = 2000;
                                maxMs = 2600;
                                break;
                            case 8:
                                minMs = 2200;
                                maxMs = 2800;
                                break;


                        }
                        RedListener.get().setMinDelay(minMs);
                        RedListener.get().setMaxDelay(maxMs);
                        App.getSP().edit().putInt("minMs", minMs).putInt("maxMs", maxMs).apply();

                        speed.setText(minMs + "-" + maxMs + "ms");
                        showToast("setDelay Config:" + minMs + "-" + maxMs + "ms");

                    }
                }).show();
    }

    public int getCoins() {
        UserList users = App.getUserList();
        if (users.isEmpty()) {
            return 0;
        }
        int coins = 0;
        for (int i = 0; i < users.size(); i++) {
            coins += Integer.valueOf(users.get(i).getCoins());
        }
        return coins;
    }

    /**
     * 监听着ListView的滑动状态改变。官方的有三种状态SCROLL_STATE_TOUCH_SCROLL、SCROLL_STATE_FLING、SCROLL_STATE_IDLE：
     * SCROLL_STATE_TOUCH_SCROLL：手指正拖着ListView滑动
     * SCROLL_STATE_FLING：ListView正自由滑动
     * SCROLL_STATE_IDLE：ListView滑动后静止
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        mListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);
    }


    /**
     * firstVisibleItem: 表示在屏幕中第一条显示的数据在adapter中的位置
     * visibleItemCount：则表示屏幕中最后一条数据在adapter中的数据，
     * totalItemCount则是在adapter中的总条数
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }
}
