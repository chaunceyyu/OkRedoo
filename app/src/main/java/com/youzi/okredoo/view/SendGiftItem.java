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
import com.youzi.okredoo.SendGiftActivity;
import com.youzi.okredoo.adapter.AppBaseAdapter;
import com.youzi.okredoo.adapter.SendGiftAdapter;
import com.youzi.okredoo.gift.GiftWindow;
import com.youzi.okredoo.model.Gift;
import com.youzi.okredoo.model.GiftPayMent;
import com.youzi.okredoo.model.Other;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.net.Api;
import com.youzi.okredoo.net.RequestUtils;
import com.youzi.okredoo.net.ResponseCallBack;
import com.youzi.okredoo.net.ServiceException;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangjiajie on 2017/10/8.
 */

public class SendGiftItem extends LinearLayout implements AppBaseAdapter.Binding<User>, View.OnClickListener {

    private User mUser;
    private SendGiftAdapter mAdapter;
    private int mPosition;

    private TextView username;
    private TextView phone;
    private TextView idCode;
    private TextView token;
    private TextView coin;
    private ImageView photo;

    private Button sendBtn;

    private SendGiftActivity mActivity;

    public SendGiftItem(Context context) {
        super(context);
    }

    public SendGiftItem(Context context, @Nullable AttributeSet attrs) {
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
        mAdapter = (SendGiftAdapter) baseAdapter;
        mPosition = position;
        mActivity = (SendGiftActivity) getContext();
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
            if (mActivity.getTargetUser() == null) {
                mActivity.showToast("无目标用户");
                return;
            }

            if (mActivity.getTargetDy() == null) {//发私人礼物
                sendPrivateGift();

            } else {//发动态礼物
                sendDyGift();
            }
        }
    }

    private void sendDyGift() {
        final GiftWindow pop = new GiftWindow(mActivity, mUser, mActivity.getTargetDy().getDid(), mActivity.getTargetDy().getUid(), "dny_" + mActivity
                .getTargetDy()
                .getDid(), GiftWindow.CIRCLE, null);
        pop.show(mActivity);
        pop.setGiftLinstener(new GiftWindow.GiftListener() {
            @Override
            public void sendGiftOk(Gift gift, int count, String incre) {
                int totalHots = Integer.parseInt(mActivity.getTargetDy().getHots()) + Integer.parseInt(incre);
//                mUser.setCoins(String.valueOf(Integer.valueOf(mUser.getCoins()) - Integer.parseInt(incre)));
                bindData();
                EventBus.getDefault().post(String.valueOf(totalHots), "gift_refreshTargetDy");
            }
        });
    }

    private void sendPrivateGift() {
        GiftWindow pop = new GiftWindow(mActivity, mUser, mActivity.getTargetUser().getUid(), mActivity.getTargetUser().getUid(), GiftWindow
                .PRIVATECHAT);
        pop.show(mActivity);
        pop.setOnSendGiftClickListener(new GiftWindow.OnSendGiftClickListener() {
            @Override
            public void toSendGift(GiftPayMent gpm, Gift choiseGift) {
                sendGiftMsg(gpm, choiseGift);
            }
        });
    }

    public void sendGiftMsg(final GiftPayMent gpm, final Gift choiseGift) {
        Map<String, String> params = new HashMap<>();
        params.put("amount", gpm.getAmount());
        params.put("conversationType", gpm.getChatType());
        params.put("count", gpm.getCount());
        params.put("gid", gpm.getGid());
        params.put("targetid", gpm.getTargetid());
        params.put("receiveId", gpm.getTargetid());
        params.put("tid", gpm.getTid());
        params.put("type", gpm.getType());
        RequestUtils.sendPostRequest(Api.SEND_GIFT, mUser.getUid(), mUser.getToken(), params, new ResponseCallBack<Other>() {
            @Override
            public void onSuccess(Other data) {
                super.onSuccess(data);
                mUser.setCoins(String.valueOf(Integer.valueOf(mUser.getCoins()) - Integer.valueOf(gpm.getAmount())));
                bindData();
                EventBus.getDefault().post("", "gift_refreshTargetUser");
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                mActivity.showToast(e.getMsg());
            }
        });


    }

}
