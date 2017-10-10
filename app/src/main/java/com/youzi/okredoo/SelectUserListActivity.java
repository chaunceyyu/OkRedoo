package com.youzi.okredoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.youzi.okredoo.adapter.AccountListAdapter;
import com.youzi.okredoo.adapter.SelectUserAdapter;
import com.youzi.okredoo.data.UserList;
import com.youzi.okredoo.model.User;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import static com.youzi.okredoo.R.id.coin;

/**
 * 选择用户界面
 */
public class SelectUserListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    private Context mContext;
    private ListView mListView;
    private SelectUserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.select_user_activity);
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
        UserList users = App.getUserList();
        mAdapter.changeDataSet(users);
    }

    private void initView() {
        mAdapter = new SelectUserAdapter(mContext);

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, SelectUserListActivity.class);
        return intent;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String uid = mAdapter.getDataList().get(i).getUid();
        Intent data = new Intent();
        data.putExtra("uid", uid);
        setResult(RESULT_OK, data);
        finish();
    }
}
