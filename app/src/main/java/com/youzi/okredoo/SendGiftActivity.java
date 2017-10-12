package com.youzi.okredoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youzi.okredoo.adapter.SendGiftAdapter;
import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.data.UserList;
import com.youzi.okredoo.model.Dynamic;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.model.User2;
import com.youzi.okredoo.net.Api;
import com.youzi.okredoo.net.RequestUtils;
import com.youzi.okredoo.net.ResponseCallBack;
import com.youzi.okredoo.net.ServiceException;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangjiajie on 2017/10/10.
 */

public class SendGiftActivity extends BaseActivity implements View.OnClickListener {

    private TextView name;
    private TextView coin;

    private TextView dyName;
    private TextView content;

    private ListView mListView;
    private SendGiftAdapter mAdapter;

    private User mTargetUser;
    private Dynamic mTargetDy;

    private Button selectBtn;
    private Button selectDyBtn;
    private ImageView photo;
    private ImageView dyPhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_gift_activity);
        initView();
        loadData();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscriber(tag = "refreshTargetUser")
    private void refreshTargetUser(String msg) {
        Map<String, String> params = new HashMap<>();
        RequestUtils.sendPostRequest(Api.GETUSERINFO, mTargetUser.getUid(), mTargetUser.getToken(), params, new ResponseCallBack<User2>() {
            @Override
            public void onSuccess(User2 u) {
                mTargetUser.setCoins(u.getCoins());
                mTargetUser.setNickName(u.getNickName());

                name.setText(mTargetUser.getNickName());
                coin.setText(mTargetUser.getCoins());

                DBManager.getInstance().updateUser(mTargetUser);
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                showToast(e.getMsg());
            }
        });
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        name = (TextView) findViewById(R.id.name);
        coin = (TextView) findViewById(R.id.coin);
        dyName = (TextView) findViewById(R.id.dyName);
        content = (TextView) findViewById(R.id.content);

        selectBtn = (Button) findViewById(R.id.selectBtn);
        selectDyBtn = (Button) findViewById(R.id.selectDyBtn);
        photo = (ImageView) findViewById(R.id.photo);
        dyPhoto = (ImageView) findViewById(R.id.dyPhoto);
        mAdapter = new SendGiftAdapter(mContext);
        mListView.setAdapter(mAdapter);

        selectBtn.setOnClickListener(this);
        selectDyBtn.setOnClickListener(this);
    }

    private void loadData() {
        UserList users = App.getUserList();
        mAdapter.changeDataSet(users);
        bindData();
    }

    private void bindData() {

    }

    public User getTargetUser() {
        return mTargetUser;
    }


    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, SendGiftActivity.class);
        return intent;
    }

    @Override
    public void onClick(View view) {
        if (view == selectBtn) {
            startActivityForResult(SelectUserListActivity.createIntent(mContext), 1);
        } else if (view == selectDyBtn) {
            if (mTargetUser == null) {
                showToast("无目标用户");
                return;
            }
            startActivityForResult(MyCircleActivity.createIntent(mContext, mTargetUser.getUid(), 1), 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String uid = data.getStringExtra("uid");
                    mTargetUser = DBManager.getInstance().getUserById(uid);
                    name.setText(mTargetUser.getNickName());
                    coin.setText(mTargetUser.getHots());
                    Glide.with(mContext).load(mTargetUser.getPhoto()).into(photo);

                    mAdapter.notifyDataSetChanged();
                }
                break;

            case 2:
                if (resultCode == RESULT_OK) {
                    Dynamic dynamic = (Dynamic) data.getSerializableExtra("dynamic");
                    mTargetDy = dynamic;
                    dyName.setText(dynamic.getContent());
                    content.setText(dynamic.getHots());
                    if (dynamic.getImages() != null && dynamic.getImages().get(0) != null) {
                        Glide.with(mContext).load(dynamic.getImages().get(0)).into(dyPhoto);
                    } else if (dynamic.getVideoCover() != null) {
                        Glide.with(mContext).load(dynamic.getVideoCover()).into(dyPhoto);
                    }

                    mAdapter.notifyDataSetChanged();
                }
                break;

        }
    }
}
