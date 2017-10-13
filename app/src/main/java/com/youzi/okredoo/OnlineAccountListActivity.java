package com.youzi.okredoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

    private TextView coin;
    private TextView userCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.online_account_list_activity);
        initView();
        loadData();

        EventBus.getDefault().register(this);
    }

    private void bindData() {
        coin.setText(String.valueOf(getCoins()) + "(" + getMoney() + ")");
        userCount.setText("账号 " + mAdapter.getCount());
    }

    private int getMoney() {
        int c = getCoins();
        return c / 32;
    }

    private int getCoins() {
        UserList users = App.getOnlineUserList();
        if (users.isEmpty()) {
            return 0;
        }
        int coins = 0;
        for (int i = 0; i < users.size(); i++) {
            try {
                coins += Integer.valueOf(users.get(i).getCoins());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return coins;
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
        bindData();
    }

    private void initView() {
        mAdapter = new OnlineAccountListAdapter(mContext);

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);
        coin = (TextView) findViewById(R.id.coin);
        userCount = (TextView) findViewById(R.id.userCount);
        mAddBtn = (Button) findViewById(R.id.addBtn);

    }

    @Override
    public void onClick(View view) {
        if (view == mAddBtn) {
            startActivity(UserLoginActivity.createIntent(mContext));
        }
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, OnlineAccountListActivity.class);
        return intent;
    }

}
