package com.youzi.okredoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.youzi.okredoo.adapter.AccountListAdapter;
import com.youzi.okredoo.data.UserList;
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
 * 账号列表界面
 */
public class AccountListActivity extends BaseActivity implements View.OnClickListener {


    private Context mContext;
    private ListView mListView;
    private AccountListAdapter mAdapter;
    private Button mAddBtn;

    private TextView coin;
    private TextView userCount;

    private HashMap<String, Integer> tokenStateMap = new HashMap<>();

    public HashMap<String, Integer> getTokenStateMap() {
        return tokenStateMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.account_list_activity);
        initView();
        loadData();

        bindData();

        getTokenState();

        EventBus.getDefault().register(this);
    }

    private void bindData() {
        coin.setText(String.valueOf(getCoins()));
        userCount.setText("账号 " + mAdapter.getCount());
    }

    private int getCoins() {
        UserList users = App.getUserList();
        if (users.isEmpty()) {
            return 0;
        }
        int coins = 0;
        for (int i = 0; i < users.size(); i++) {
            coins += Integer.valueOf(users.get(i).getCoins());
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
        UserList users = App.getUserList();
        mAdapter.changeDataSet(users);
        bindData();


    }

    private void getTokenState() {
        UserList users = mAdapter.getDataList();
        for (int i = 0; i < users.size(); i++) {
            final User user = users.get(i);
            Map<String, String> params = new HashMap<>();
            RequestUtils.sendPostRequest(Api.GETUSERINFO, user.getUid(), user.getToken(), params, new ResponseCallBack<User2>() {
                @Override
                public void onSuccess(User2 u) {
                    tokenStateMap.put(user.getUid(), 1);
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(ServiceException e) {
                    super.onFailure(e);
                    tokenStateMap.put(user.getUid(), 0);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void initView() {
        mAdapter = new AccountListAdapter(mContext);

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);

        mAddBtn = (Button) findViewById(R.id.addBtn);
        coin = (TextView) findViewById(R.id.coin);
        userCount = (TextView) findViewById(R.id.userCount);
        mAddBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mAddBtn) {
            startActivity(GetUserInfoActivity.createIntent(mContext));
        }
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, AccountListActivity.class);
        return intent;
    }
}
