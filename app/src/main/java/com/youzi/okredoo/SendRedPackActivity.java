package com.youzi.okredoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.ListView;

import com.youzi.okredoo.adapter.SendPackUserListAdapter;
import com.youzi.okredoo.data.UserList;
import com.youzi.okredoo.model.User;

/**
 * Created by zhangjiajie on 2017/10/10.
 */

public class SendRedPackActivity extends BaseActivity {

    private EditText uidEdit;

    private ListView mListView;
    private SendPackUserListAdapter mAdapter;

    private User mTargetUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_pack_activity);
        initView();
        loadData();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        uidEdit = (EditText) findViewById(R.id.uidEdit);
        mAdapter = new SendPackUserListAdapter(mContext);
        mListView.setAdapter(mAdapter);
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

}
