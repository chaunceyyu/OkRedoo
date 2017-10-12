package com.youzi.okredoo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youzi.okredoo.R;
import com.youzi.okredoo.adapter.AppBaseAdapter;
import com.youzi.okredoo.adapter.TongJiAdapter;
import com.youzi.okredoo.model.TongJi1;

/**
 * Created by zhangjiajie on 2017/10/8.
 */

public class TongJiItem extends LinearLayout implements AppBaseAdapter.Binding<TongJi1>, View.OnClickListener {

    private TongJi1 mTongJi1;
    private TongJiAdapter mAdapter;
    private int mPosition;

    private TextView coin;
    private TextView date;

    public TongJiItem(Context context) {
        super(context);
    }

    public TongJiItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        coin = findViewById(R.id.coin);
        date = findViewById(R.id.date);

    }

    @Override
    public void bind(TongJi1 data, AppBaseAdapter baseAdapter, int position) {
        mTongJi1 = data;
        mAdapter = (TongJiAdapter) baseAdapter;
        mPosition = position;

        bindData();
    }

    private void bindData() {
        coin.setText(String.valueOf(mTongJi1.getCount()) + "(" + getMoney() + ")");
        date.setText(mTongJi1.getDate());
    }

    private int getMoney() {
        int c = mTongJi1.getCount();
        return c / 32;
    }

    @Override
    public void unBind() {

    }

    @Override
    public void onClick(View view) {
    }
}
