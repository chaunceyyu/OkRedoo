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
import com.youzi.okredoo.model.User;

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
        }
    }
}
