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
import com.youzi.okredoo.GetUserInfoActivity;
import com.youzi.okredoo.R;
import com.youzi.okredoo.adapter.AppBaseAdapter;
import com.youzi.okredoo.adapter.AccountListAdapter;
import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.model.User;

import org.simple.eventbus.EventBus;

/**
 * Created by zhangjiajie on 2017/10/8.
 */

public class AccountListItem extends LinearLayout implements AppBaseAdapter.Binding<User>, View.OnClickListener {

    private User mUser;
    private AccountListAdapter mAdapter;
    private int mPosition;

    private TextView username;
    private TextView phone;
    private TextView idCode;
    private TextView token;
    private TextView coin;
    private ImageView photo;

    private Button mEditBtn;
    private Button mDeleteBtn;

    public AccountListItem(Context context) {
        super(context);
    }

    public AccountListItem(Context context, @Nullable AttributeSet attrs) {
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

        mEditBtn = findViewById(R.id.editBtn);
        mDeleteBtn = findViewById(R.id.deleteBtn);

        mEditBtn.setOnClickListener(this);
        mDeleteBtn.setOnClickListener(this);
    }

    @Override
    public void bind(User data, AppBaseAdapter baseAdapter, int position) {
        mUser = data;
        mAdapter = (AccountListAdapter) baseAdapter;
        mPosition = position;

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
        if (view == mDeleteBtn) {
            DBManager.getInstance().deleteUser(mUser.getUid());
            EventBus.getDefault().post(mUser.getUid(), "delete_user");
        } else if (view == mEditBtn) {
            getContext().startActivity(GetUserInfoActivity.createIntent(getContext(), mUser.getUid()));
        }
    }
}
