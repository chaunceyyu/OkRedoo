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
import com.youzi.okredoo.adapter.SendPackUserListAdapter;
import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.data.UserList;
import com.youzi.okredoo.model.User;

import static com.youzi.okredoo.R.id.photo;

/**
 * Created by zhangjiajie on 2017/10/10.
 */

public class SendRedPackActivity extends BaseActivity implements View.OnClickListener {

    private TextView name;
    private TextView coin;

    private ListView mListView;
    private SendPackUserListAdapter mAdapter;

    private User mTargetUser;

    private Button selectBtn;
    private ImageView photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_pack_activity);
        initView();
        loadData();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        name = (TextView) findViewById(R.id.name);
        coin = (TextView) findViewById(R.id.coin);
        selectBtn = (Button) findViewById(R.id.selectBtn);
        photo = (ImageView) findViewById(R.id.photo);
        mAdapter = new SendPackUserListAdapter(mContext);
        mListView.setAdapter(mAdapter);

        selectBtn.setOnClickListener(this);
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
        Intent intent = new Intent(context, SendRedPackActivity.class);
        return intent;
    }

    @Override
    public void onClick(View view) {
        if (view == selectBtn) {
            startActivityForResult(SelectUserListActivity.createIntent(mContext), 1);
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
                    coin.setText(mTargetUser.getCoins());
                    Glide.with(mContext).load(mTargetUser.getPhoto()).into(photo);
                }
                break;
        }
    }
}
