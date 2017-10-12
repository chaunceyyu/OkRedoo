package com.youzi.okredoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;

import com.youzi.okredoo.adapter.TongJiAdapter;
import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.model.TongJi1;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 统计界面
 * Created by zhangjiajie on 2017/10/10.
 */

public class StatisticsActivity extends BaseActivity {

    @ViewInject(R.id.listview)
    private ListView mListView;

    @ViewInject(R.id.coin)
    private TextView coin;

    private TongJiAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_activity);
        x.view().inject(this);
        mAdapter = new TongJiAdapter(mContext);

        mListView.setAdapter(mAdapter);

        loadData();
    }

    private void loadData() {
        ArrayList<TongJi1> tongJi1ArrayList = DBManager.getInstance().GetTongJi1List();
        mAdapter.changeDataSet(tongJi1ArrayList);

        bindData();
    }

    private void bindData() {
        coin.setText(String.valueOf(getCoins()) + "(" + getMoney() + ")");
    }

    private int getMoney() {
        int c = getCoins();
        return c / 32;
    }

    private int getCoins() {
        ArrayList<TongJi1> tongJi1 = mAdapter.getDataList();
        if (tongJi1 == null || tongJi1.isEmpty()) {
            return 0;
        }
        int coins = 0;
        for (int i = 0; i < tongJi1.size(); i++) {
            coins += tongJi1.get(i).getCount();
        }
        return coins;
    }


    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, StatisticsActivity.class);
        return intent;
    }
}
