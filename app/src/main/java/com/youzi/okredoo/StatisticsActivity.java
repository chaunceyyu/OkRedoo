package com.youzi.okredoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 统计界面
 * Created by zhangjiajie on 2017/10/10.
 */

public class StatisticsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_activity);
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, StatisticsActivity.class);
        return intent;
    }
}
