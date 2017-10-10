package com.youzi.okredoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by zhangjiajie on 2017/10/10.
 */

public class SendRedPackActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_pack_activity);
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, SendRedPackActivity.class);
        return intent;
    }
}
