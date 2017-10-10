package com.youzi.okredoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.youzi.okredoo.adapter.OnlineAccountListAdapter;
import com.youzi.okredoo.data.UserList;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;


/**
 * 在线账号列表界面
 */
public class OnlineAccountListActivity extends BaseActivity implements View.OnClickListener {


    private Context mContext;
    private ListView mListView;
    private OnlineAccountListAdapter mAdapter;
    private Button mAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.account_list_activity);
        initView();
        loadData();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscriber(tag = "refresh_user_list")
    private void refreshUserList(String msg) {
        loadData();
    }

    private void loadData() {
        UserList users = App.getOnlineUserList();
        if (users != null) {
            mAdapter.changeDataSet(users);
        }
    }

    private void initView() {
        findViewById(R.id.topLayout).setVisibility(View.GONE);
        mAdapter = new OnlineAccountListAdapter(mContext);

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);

        mAddBtn = (Button) findViewById(R.id.addBtn);

    }

    @Override
    public void onClick(View view) {
        if (view == mAddBtn) {
            startActivity(GetUserInfoActivity.createIntent(mContext));
        }
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, OnlineAccountListActivity.class);
        return intent;
    }

}
