package com.youzi.okredoo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.youzi.okredoo.R;
import com.youzi.okredoo.SendCoinActivity;
import com.youzi.okredoo.adapter.AppBaseAdapter;
import com.youzi.okredoo.adapter.SendCoinAdapter;
import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.model.RedPackInfo;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.model.response.Chairesponse;
import com.youzi.okredoo.model.response.HongbaosendResponse;
import com.youzi.okredoo.net.Api;
import com.youzi.okredoo.net.ApiCallback;
import com.youzi.okredoo.net.RequestUtils;
import com.youzi.okredoo.net.ResponseCallBack;
import com.youzi.okredoo.net.ServiceException;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import io.rong.imlib.model.Conversation;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by zhangjiajie on 2017/10/8.
 */

public class SendCoinItem extends LinearLayout implements AppBaseAdapter.Binding<User>, View.OnClickListener {

    private User mUser;
    private SendCoinAdapter mAdapter;
    private int mPosition;

    private TextView username;
    private TextView phone;
    private TextView idCode;
    private TextView token;
    private TextView coin;
    private ImageView photo;

    private Button sendBtn;

    private SendCoinActivity mActivity;

    public SendCoinItem(Context context) {
        super(context);
    }

    public SendCoinItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        idCode = findViewById(R.id.idcode);
        token = findViewById(R.id.token);
        coin = findViewById(R.id.coin);
        photo = findViewById(R.id.photo);

        sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(this);
    }

    @Override
    public void bind(User data, AppBaseAdapter baseAdapter, int position) {
        mUser = data;
        mAdapter = (SendCoinAdapter) baseAdapter;
        mPosition = position;
        mActivity = (SendCoinActivity) getContext();
        bindData();
    }

    private void bindData() {

        username.setText(mUser.getNickName());
        phone.setText(mUser.getPhone());
        idCode.setText(mUser.getUnumber());
        token.setText(mUser.getToken());
        coin.setText(mUser.getCoins());

        if (mActivity.getTargetUser() != null &&
                mActivity.getTargetUser().getUid().equals(mUser.getUid())) {
            sendBtn.setEnabled(false);
        } else {
            sendBtn.setEnabled(true);
        }

        Glide.with(getContext()).load(mUser.getPhoto()).into(photo);
    }

    @Override
    public void unBind() {

    }

    @Override
    public void onClick(View view) {
        if (view == sendBtn) {
            if (mActivity.getTargetUser() == null) {
                mActivity.showToast("无目标用户");
                return;
            }
            sendHongbao();
        }
    }

    private void sendHongbao() {
//        conversationType	1私聊，2讨论组，3群聊，4聊天室	string
//        count	红包数	string
//        ext	扩展信息	string
//        pass	口令	string
        Map<String, String> params = new HashMap<>();
        params.put("targetid", mActivity.getTargetUser().getUid());
        params.put("conversationType", String.valueOf(Conversation.ConversationType.CHATROOM.getValue()));
        params.put("amount", mUser.getCoins());
        params.put("count", "1");
        params.put("ext", "恭喜发财");
        params.put("pass", "");
        RequestUtils.sendPostRequest(Api.SEND_HONGBAO, mUser.getUid(), mUser.getToken(), params, new ResponseCallBack<HongbaosendResponse>() {
            @Override
            public void onSuccess(final HongbaosendResponse data) {
                mActivity.showToast("发送金币成功");
                mUser.setCoins(data.getAmount());
                DBManager.getInstance().updateUser(mUser);
                bindData();
                sendBtn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        redPackROB(data.getRpid(), mActivity.getTargetUser(), String.valueOf(Conversation.ConversationType.CHATROOM
                                .getValue()), mActivity.getTargetUser().getUid());
                    }
                }, 1000);

            }


            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                mActivity.showToast(e.getMsg());
            }
        });
    }

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
                EventBus.getDefault().post("", "refreshTargetUser");
                getRedInfo(rpid, user);

            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                mActivity.showToast(e.getMsg());
            }

            private void getRedInfo(String rpid, User user) {
                redPackReciveState(rpid, user, new ApiCallback<RedPackInfo>() {
                    @Override
                    public void onSuccess(RedPackInfo data) {
                        Log.w(TAG, "redPackReciveState onSuccess:" + new Gson().toJson(data));
                    }

                    @Override
                    public void onFailure(ServiceException e) {
                        super.onFailure(e);
                        Log.e(TAG, "redPackReciveState onFailure:" + new Gson().toJson(e));
                    }
                });
            }
        });
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
}
