package com.youzi.okredoo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youzi.okredoo.R;
import com.youzi.okredoo.adapter.AppBaseAdapter;
import com.youzi.okredoo.adapter.ListenerDataListAdapter;

/**
 * Created by zhangjiajie on 2017/10/8.
 */

public class ListenerDataListItem extends LinearLayout implements AppBaseAdapter.Binding<String>, View.OnClickListener {

    private String msg;
    private ListenerDataListAdapter mAdapter;
    private int mPosition;

    private TextView content;

    public ListenerDataListItem(Context context) {
        super(context);
    }

    public ListenerDataListItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        content = findViewById(R.id.content);
    }

    @Override
    public void bind(String data, AppBaseAdapter baseAdapter, int position) {
        msg = data;
        mAdapter = (ListenerDataListAdapter) baseAdapter;
        mPosition = position;

        bindData();
    }

    private void bindData() {
        content.setText(msg);
    }

    @Override
    public void unBind() {

    }

    @Override
    public void onClick(View view) {
    }
}
