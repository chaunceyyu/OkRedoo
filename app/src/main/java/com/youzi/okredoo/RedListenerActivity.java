package com.youzi.okredoo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.youzi.okredoo.adapter.ListenerDataListAdapter;

/**
 * 监听数据界面
 * Created by zhangjiajie on 2017/10/9.
 */

public class RedListenerActivity extends BaseActivity {

    private ListView mListView;

    private ListenerDataListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.red_listener_activity);

        initView();
    }

    private void initView() {
        mAdapter = new ListenerDataListAdapter(mContext);

        mListView = (ListView) findViewById(R.id.listview);

        mListView.setAdapter(mAdapter);
    }
}
