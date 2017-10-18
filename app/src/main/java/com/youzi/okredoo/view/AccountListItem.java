package com.youzi.okredoo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youzi.okredoo.AccountListActivity;
import com.youzi.okredoo.App;
import com.youzi.okredoo.GetMoneyActivity;
import com.youzi.okredoo.H5Activity;
import com.youzi.okredoo.MyCircleActivity;
import com.youzi.okredoo.R;
import com.youzi.okredoo.UserInfoEditActivity;
import com.youzi.okredoo.UserLoginActivity;
import com.youzi.okredoo.adapter.AccountListAdapter;
import com.youzi.okredoo.adapter.AppBaseAdapter;
import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.model.MyExperience;
import com.youzi.okredoo.model.MyHots;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.net.Api;
import com.youzi.okredoo.net.RequestUtils;
import com.youzi.okredoo.net.ResponseCallBack;
import com.youzi.okredoo.net.ServiceException;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import static com.youzi.okredoo.R.id.uid;

/**
 * Created by zhangjiajie on 2017/10/8.
 */

public class AccountListItem extends LinearLayout implements AppBaseAdapter.Binding<User>, View.OnClickListener {

    private User mUser;
    private AccountListAdapter mAdapter;
    private int mPosition;

    private TextView username;
    private TextView phone;
    private TextView idCode;
    private TextView token;
    private TextView coin;
    private TextView piao;
    private TextView tudouni;

    private TextView tokenState;

    private ImageView photo;

    private Button mEditBtn;
    private Button mDeleteBtn;
    private Button editInfoBtn;
    private Button addOnlineBtn;
    private Button tixianBtn;
    private Button dyBtn;

    private AccountListActivity mActivity;

    public HashMap<String, MyHots> mMyHotsHashMap = new HashMap<>();
    public HashMap<String, MyExperience> mMyExperienceHashMap = new HashMap<>();

    public AccountListItem(Context context) {
        super(context);
    }

    public AccountListItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        idCode = findViewById(R.id.idcode);
        token = findViewById(R.id.token);
        coin = findViewById(R.id.coin);
        piao = findViewById(R.id.piao);
        tudouni = findViewById(R.id.douni);
        photo = findViewById(R.id.photo);
        tokenState = findViewById(R.id.tokenState);

        mEditBtn = findViewById(R.id.editBtn);
        mDeleteBtn = findViewById(R.id.deleteBtn);
        editInfoBtn = findViewById(R.id.editInfoBtn);
        addOnlineBtn = findViewById(R.id.addOnlineBtn);
        tixianBtn = findViewById(R.id.tixianBtn);
        dyBtn = findViewById(R.id.dyBtn);

        mEditBtn.setOnClickListener(this);
        mDeleteBtn.setOnClickListener(this);
        editInfoBtn.setOnClickListener(this);
        addOnlineBtn.setOnClickListener(this);
        tixianBtn.setOnClickListener(this);
        dyBtn.setOnClickListener(this);
    }

    @Override
    public void bind(User data, AppBaseAdapter baseAdapter, int position) {
        mUser = data;
        mAdapter = (AccountListAdapter) baseAdapter;
        mPosition = position;
        mActivity = (AccountListActivity) getContext();

        bindData();
    }

    private void bindData() {

        if (mUser.getOnline() == 1) {
            addOnlineBtn.setEnabled(false);
            addOnlineBtn.setText("已激活");
        } else {
            addOnlineBtn.setEnabled(true);
            addOnlineBtn.setText("激活");
        }

        Integer state = mActivity.getTokenStateMap().get(mUser.getUid());
        if (state == null) {
            tokenState.setVisibility(INVISIBLE);
        } else {
            tokenState.setVisibility(VISIBLE);
            if (state == 1) {
                tokenState.setText("(生效)");
                tokenState.setTextColor(getResources().getColor(R.color.green_weixin));
            } else {
                tokenState.setText("(失效)");
                tokenState.setTextColor(getResources().getColor(R.color.red_weixing));
            }
        }

        username.setText(mUser.getNickName());
        if (TextUtils.isEmpty(mUser.getPhone())) {
            phone.setText("(第三方登录)");
        } else {
            phone.setText(mUser.getPhone() + "(手机登录)");
        }

        idCode.setText(mUser.getUnumber());
        token.setText(mUser.getToken());
        coin.setText(mUser.getCoins());

        coin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://h5.tudouni.doubozhibo.com/tudouni/html/rebate.html?uid=" + mUser.getUid() + "&token=" + mUser.getToken() +
                        "&type=1";
                getContext().startActivity(H5Activity.newIntent(getContext(), url, mUser.getUid()));
            }
        });


        Glide.with(getContext()).load(mUser.getPhoto()).into(photo);

        MyHots myHots = mMyHotsHashMap.get(mUser.getUid());
        if (myHots != null) {
            piao.setText(myHots.getHots());
        } else {
            loadHots();
        }

        piao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://h5.tudouni.doubozhibo.com/tudouni/html/lp_get.html?uid=" + mUser.getUid() + "&token=" + mUser.getToken();
                getContext().startActivity(H5Activity.newIntent(getContext(), url, mUser.getUid()));
            }
        });

        MyExperience myExperience = mMyExperienceHashMap.get(mUser.getUid());
        if (myExperience != null) {
            tudouni.setText(myExperience.getBalance());
        } else {
            loadDouNi();
        }

        tudouni.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(H5Activity.newIntent(getContext(), App.H5_SHOP, mUser.getUid()));
            }
        });

    }

    private void loadHots() {
        Map<String, String> params = new HashMap<>();
        RequestUtils.sendPostRequest(Api.ACCOUNT_INCOME, mUser.getUid(), mUser.getToken(), params, new ResponseCallBack<MyHots>() {
            @Override
            public void onSuccess(MyHots info) {
                mMyHotsHashMap.put(mUser.getUid(), info);
                piao.setText(info.getHots());
            }

            @Override
            public void onFailure(ServiceException e) {
                mActivity.showToast(e.getMsg());
            }
        });

    }

    private void loadDouNi() {
        Map<String, String> params = new HashMap<>();
        RequestUtils.sendPostRequest(Api.MY_EXPERIENCE, mUser.getUid(), mUser.getToken(), params, new ResponseCallBack<MyExperience>() {
            @Override
            public void onSuccess(MyExperience info) {
                mMyExperienceHashMap.put(mUser.getUid(), info);
                tudouni.setText(info.getBalance());
            }

            @Override
            public void onFailure(ServiceException e) {
                mActivity.showToast(e.getMsg());
            }
        });
    }

    @Override
    public void unBind() {

    }

    @Override
    public void onClick(View view) {
        if (view == mDeleteBtn) {
            DBManager.getInstance().deleteUser(mUser.getUid());
            EventBus.getDefault().post(mUser.getUid(), "refresh_user_list");
        } else if (view == mEditBtn) {
            if (mUser.getPhone() != null && mUser.getPwd() != null) {
                getContext().startActivity(UserLoginActivity.createIntentForPhone(getContext(), mUser.getPhone(), mUser.getPwd()));
            } else {
                getContext().startActivity(UserLoginActivity.createIntentForUid(getContext(), mUser.getUid(), mUser.getToken()));
            }

        } else if (view == addOnlineBtn) {
            mUser.setOnline(1);
            DBManager.getInstance().updateUser(mUser);
            bindData();
        } else if (view == editInfoBtn) {
            getContext().startActivity(UserInfoEditActivity.createIntent(getContext(), mUser.getUid()));
        } else if (view == tixianBtn) {
            getContext().startActivity(GetMoneyActivity.createIntent(getContext(), mUser.getUid()));
        } else if (view == dyBtn) {
            getContext().startActivity(MyCircleActivity.createIntent(getContext(), mUser.getUid(), 0));
        }
    }
}
