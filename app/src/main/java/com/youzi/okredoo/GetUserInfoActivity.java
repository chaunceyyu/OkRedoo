package com.youzi.okredoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.data.UserList;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.net.Api;
import com.youzi.okredoo.net.RequestUtils;
import com.youzi.okredoo.net.ResponseCallBack;
import com.youzi.okredoo.net.ServiceException;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;


/**
 * 获取用户资料界面
 * Created by zhangjiajie on 2017/10/8.
 */

public class GetUserInfoActivity extends BaseActivity {


    private Context mContext;

    private EditText mUsername;
    private EditText mPhoneNumber;
    private EditText mPassword;
    private EditText mToken;

    private Button mSavebBtn;
    private Button mGetTokenBtn;

    private User mUser;

    private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.get_user_info_activity);

        mUsername = (EditText) findViewById(R.id.username);
        mPhoneNumber = (EditText) findViewById(R.id.phone);
        mPassword = (EditText) findViewById(R.id.password);
        mToken = (EditText) findViewById(R.id.token);

        mSavebBtn = (Button) findViewById(R.id.saveBtn);
        mSavebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uid == null) {
                    DBManager.getInstance().saveUser(mUser);
                } else {
                    DBManager.getInstance().updateUser(mUser);
                }
                Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post("", "refresh_user_list");
                finish();
            }
        });

        mGetTokenBtn = (Button) findViewById(R.id.getTokenBtn);

        mGetTokenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userName = mPhoneNumber.getText().toString();
                final String password = mPassword.getText().toString();
                Map<String, String> params = new HashMap<>();
                params.put("user", userName);
                params.put("password", password);
                RequestUtils.sendPostRequest(Api.USERNAME_PASSWOED_LOGIN, params, new ResponseCallBack<User>() {

                    @Override
                    public void onSuccess(User user) {
                        super.onSuccess(user);

                        mUser = user;
                        mUser.setPhone(userName);
                        mUser.setPwd(password);

                        bindData();
                    }

                    @Override
                    public void onFailure(ServiceException e) {
                        super.onFailure(e);
                        Toast.makeText(mContext, e.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        uid = getIntent().getStringExtra("uid");
        if (uid != null) {
            mUser = DBManager.getInstance().getUserById(uid);
            bindData();
        }
    }

    private void bindData() {
        mUsername.setText(mUser.getNickName());
        mToken.setText(mUser.getToken());
        mPhoneNumber.setText(mUser.getPhone());
        mPassword.setText(mUser.getPwd());
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, GetUserInfoActivity.class);
        return intent;
    }

    public static Intent createIntent(Context context, String uid) {
        Intent intent = new Intent(context, GetUserInfoActivity.class);
        intent.putExtra("uid", uid);
        return intent;
    }
}
