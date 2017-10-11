package com.youzi.okredoo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.model.MyHots;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.net.Api;
import com.youzi.okredoo.net.RequestUtils;
import com.youzi.okredoo.net.ResponseCallBack;
import com.youzi.okredoo.net.ServiceException;

import org.simple.eventbus.EventBus;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangjiajie on 2017/10/11.
 */

public class GetMoneyActivity extends BaseActivity {

    private User mUser;
    private String uid;

    @ViewInject(R.id.tvTotalHots)
    private TextView tvTotalHots;

    @ViewInject(R.id.tvHots)
    private TextView tvHots;

    @ViewInject(R.id.tvCash)
    private TextView tvCash;

    @ViewInject(R.id.tixianEdit)
    private EditText tixianEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_money_activity);
        x.view().inject(this);
        uid = getIntent().getStringExtra("uid");
        if (uid != null) {
            mUser = DBManager.getInstance().getUserById(uid);
        }

        loadInfo();

    }

    private void loadInfo() {
        Map<String, String> params = new HashMap<>();
        RequestUtils.sendPostRequest(Api.ACCOUNT_INCOME, mUser.getUid(), mUser.getToken(), params, new ResponseCallBack<MyHots>() {
            @Override
            public void onSuccess(MyHots info) {
                bindData(info);
            }

            @Override
            public void onFailure(ServiceException e) {
                showToast(e.getMsg());
            }
        });

    }

    private void bindData(MyHots info) {
        tvTotalHots.setText(info.getTotalHots());
        tvCash.setText(info.getCash());
        tvHots.setText(info.getHots());

        tixianEdit.setText(info.getCash());

    }

    @Event(R.id.tvWithdrawLog)
    private void clickWithdrawLog(View view) {
        String url = "https://h5.tudouni.doubozhibo.com/tudouni/html/lp_get.html?uid=" + mUser.getUid() + "&token=" + mUser.getToken();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }

    @Event(R.id.tixianBtn)
    private void clickTiXian(View view) {
        String s = tixianEdit.getText().toString().trim();
        int money = Integer.parseInt(s);

        if (money >= 100) {
            getTaxMoney(money);
        } else {
            showToast("提现金额要大于100");
        }

    }

    public static Intent createIntent(Context context, String uid) {
        Intent intent = new Intent(context, GetMoneyActivity.class);
        intent.putExtra("uid", uid);
        return intent;
    }

    /**
     * @param money 提现金额
     */
    private void getTaxMoney(final int money) {
        showProgress("提现", "正在处理...");
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "alipay");
        params.put("hots", String.valueOf(money));
        RequestUtils.sendPostRequest(Api.WITHDRAW_HOTS, mUser.getUid(), mUser.getToken(), params, new ResponseCallBack<Object>() {
            @Override
            public void onSuccess(Object data) {
                closeProgress();
                showToast("提现成功");
                finish();
                EventBus.getDefault().post("", "refresh_user_list");
            }

            @Override
            public void onFailure(ServiceException e) {
                closeProgress();
                showToast(e.getMsg());
            }
        });
    }
}
