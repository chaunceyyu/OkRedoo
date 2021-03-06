package com.youzi.okredoo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.net.Api;
import com.youzi.okredoo.net.RequestUtils;
import com.youzi.okredoo.net.ResponseCallBack;
import com.youzi.okredoo.net.ServiceException;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import static com.youzi.okredoo.R.id.token;
import static com.youzi.okredoo.R.id.uid;


/**
 * 获取用户资料界面
 * Created by zhangjiajie on 2017/10/8.
 */

public class UserLoginActivity extends BaseActivity {


    private Context mContext;

    private EditText mUsername;
    private EditText mPhoneNumber;
    private EditText mPassword;
    private EditText mToken;
    private EditText uidEdit;

    //    private Button mSavebBtn;
    private Button mGetTokenBtn;
    private Button importBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.get_user_info_activity);

        mUsername = (EditText) findViewById(R.id.username);
        mPhoneNumber = (EditText) findViewById(R.id.phone);
        mPassword = (EditText) findViewById(R.id.password);
        mToken = (EditText) findViewById(token);
        uidEdit = (EditText) findViewById(uid);

//        mSavebBtn = (Button) findViewById(R.id.saveBtn);
//        mSavebBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!TextUtils.isEmpty(uidEdit.getText()) && !TextUtils.isEmpty(mToken.getText())) {
//                    Map<String, String> params = new HashMap<>();
//                    RequestUtils.sendPostRequest(Api.GETUSERINFO, uidEdit.getText().toString(), mToken.getText().toString(), params, new
//                            ResponseCallBack<User>() {
//                                @Override
//                                public void onSuccess(User u) {
//                                    if (DBManager.getInstance().isExistUser(u.getUid())) {
//                                        DBManager.getInstance().updateUser(u);
//                                    } else {
//                                        DBManager.getInstance().saveUser(u);
//                                    }
//
//                                    showToast("保存成功");
//                                    EventBus.getDefault().post("", "refresh_user_list");
//                                    finish();
//                                }
//
//                                @Override
//                                public void onFailure(ServiceException e) {
//                                    super.onFailure(e);
//                                    showToast(e.getMsg());
//                                }
//                            });
//
//                } else {
//                    showToast("token无效");
//                }
//            }
//        });

        mGetTokenBtn = (Button) findViewById(R.id.getTokenBtn);
        importBtn = (Button) findViewById(R.id.importBtn);

        mGetTokenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mPhoneNumber.getText()) && !TextUtils.isEmpty(mPassword.getText())) {
                    final String userName = mPhoneNumber.getText().toString();
                    final String password = mPassword.getText().toString();
                    Map<String, String> params = new HashMap<>();
                    params.put("user", userName);
                    params.put("password", password);
                    RequestUtils.sendPostRequest(Api.USERNAME_PASSWOED_LOGIN, params, new ResponseCallBack<User>() {

                        @Override
                        public void onSuccess(User u) {
                            super.onSuccess(u);

                            u.setPhone(userName);
                            u.setPwd(password);

                            if (DBManager.getInstance().isExistUser(u.getUid())) {
                                DBManager.getInstance().updateUser(u);
                            } else {
                                DBManager.getInstance().saveUser(u);
                            }

                            showToast("登录成功");
                            EventBus.getDefault().post("", "refresh_user_list");
                            EventBus.getDefault().post(u, "getTokenState");
                            finish();
                        }

                        @Override
                        public void onFailure(ServiceException e) {
                            super.onFailure(e);
                            showToast(e.getMsg());
                        }
                    });
                } else if (!TextUtils.isEmpty(uidEdit.getText()) && !TextUtils.isEmpty(mToken.getText())) {
                    Map<String, String> params = new HashMap<>();
                    RequestUtils.sendPostRequest(Api.GETUSERINFO, uidEdit.getText().toString(), mToken.getText().toString(), params, new
                            ResponseCallBack<User>() {
                                @Override
                                public void onSuccess(User u) {
                                    u.setToken(mToken.getText().toString());
                                    if (DBManager.getInstance().isExistUser(u.getUid())) {
                                        DBManager.getInstance().updateUser(u);
                                    } else {
                                        DBManager.getInstance().saveUser(u);
                                    }

                                    showToast("登录成功");
                                    EventBus.getDefault().post("", "refresh_user_list");
                                    EventBus.getDefault().post(u, "getTokenState");
                                    finish();
                                }

                                @Override
                                public void onFailure(ServiceException e) {
                                    super.onFailure(e);
                                    showToast(e.getMsg());
                                }
                            });
                } else {
                    showToast("账号参数无效");
                }

            }
        });


        importBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = cm.getPrimaryClip();
                if (clipData != null && clipData.getItemAt(0) != null) {
                    String s = clipData.getItemAt(0).getText().toString();
                    if (!TextUtils.isEmpty(s)) {
                        String[] str = s.split(",");
                        if (str.length == 2) {
                            String uid = str[0];
                            String token = str[1];

                            uidEdit.setText(uid);
                            mToken.setText(token);
                        }
                    }
                }
            }
        });

//        uid = getIntent().getStringExtra("uid");
//        if (uid != null) {
//            mUser = DBManager.getInstance().getUserById(uid);
//            bindData();
//        }
        bindData();
    }

    private void bindData() {
//        mUsername.setText(mUser.getNickName());
        mToken.setText(getIntent().getStringExtra("token"));
        mPhoneNumber.setText(getIntent().getStringExtra("phone"));
        mPassword.setText(getIntent().getStringExtra("password"));
        uidEdit.setText(getIntent().getStringExtra("uid"));
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, UserLoginActivity.class);
        return intent;
    }

    public static Intent createIntentForUid(Context context, String uid, String token) {
        Intent intent = new Intent(context, UserLoginActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("token", token);
        return intent;
    }

    public static Intent createIntentForPhone(Context context, String phone, String password) {
        Intent intent = new Intent(context, UserLoginActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("password", password);
        return intent;
    }
}
