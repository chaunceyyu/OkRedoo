package com.youzi.okredoo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youzi.okredoo.R;
import com.youzi.okredoo.SendRedPackActivity;
import com.youzi.okredoo.adapter.AppBaseAdapter;
import com.youzi.okredoo.adapter.SendPackUserListAdapter;
import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.model.response.HongbaosendResponse;
import com.youzi.okredoo.net.Api;
import com.youzi.okredoo.net.RedListener;
import com.youzi.okredoo.net.RequestUtils;
import com.youzi.okredoo.net.ResponseCallBack;
import com.youzi.okredoo.net.ServiceException;

import java.util.HashMap;
import java.util.Map;

import io.rong.imlib.model.Conversation;

/**
 * Created by zhangjiajie on 2017/10/8.
 */

public class SendPackUserListItem extends LinearLayout implements AppBaseAdapter.Binding<User>, View.OnClickListener {

    private User mUser;
    private SendPackUserListAdapter mAdapter;
    private int mPosition;

    private TextView username;
    private TextView phone;
    private TextView idCode;
    private TextView token;
    private TextView coin;
    private ImageView photo;

    private Button sendBtn;

    private SendRedPackActivity mActivity;

    public SendPackUserListItem(Context context) {
        super(context);
    }

    public SendPackUserListItem(Context context, @Nullable AttributeSet attrs) {
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
        mAdapter = (SendPackUserListAdapter) baseAdapter;
        mPosition = position;
        mActivity = (SendRedPackActivity) getContext();
        bindData();
    }

    private void bindData() {

        username.setText(mUser.getNickName());
        phone.setText(mUser.getPhone());
        idCode.setText(mUser.getUnumber());
        token.setText(mUser.getToken());
        coin.setText(mUser.getCoins());

        Glide.with(getContext()).load(mUser.getPhoto()).into(photo);
    }

    @Override
    public void unBind() {

    }

    @Override
    public void onClick(View view) {
        if (view == sendBtn) {
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
//        params.put("amount", mUser.getCoins());
        params.put("amount", "1");
        params.put("count", "1");
        params.put("ext", "恭喜发财");
        params.put("pass", "");
        RequestUtils.sendPostRequest(Api.SEND_HONGBAO, mUser.getUid(), mUser.getToken(), params, new ResponseCallBack<HongbaosendResponse>() {
            @Override
            public void onSuccess(final HongbaosendResponse data) {
                mActivity.showToast("发送成功，剩余金币" + data.getAmount());
                mUser.setCoins(data.getAmount());
                DBManager.getInstance().updateUser(mUser);
                bindData();
                sendBtn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RedListener.get().redPackROB(data.getRpid(), mActivity.getTargetUser(), String.valueOf(Conversation.ConversationType.CHATROOM
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
}
