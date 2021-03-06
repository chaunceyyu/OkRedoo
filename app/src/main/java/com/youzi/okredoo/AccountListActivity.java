package com.youzi.okredoo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.youzi.okredoo.adapter.AccountListAdapter;
import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.data.UserList;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.model.User2;
import com.youzi.okredoo.net.Api;
import com.youzi.okredoo.net.RequestUtils;
import com.youzi.okredoo.net.ResponseCallBack;
import com.youzi.okredoo.net.ServiceException;
import com.youzi.okredoo.util.AppUtil;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.Map;

/**
 * 账号列表界面
 */
public class AccountListActivity extends BaseActivity implements View.OnClickListener {


    private Context mContext;
    private ListView mListView;
    private AccountListAdapter mAdapter;
    private Button mAddBtn;
    private Button importBtn;
    private Button activeBtn;

    private TextView coin;
    private TextView userCount;

    private HashMap<String, Integer> tokenStateMap = new HashMap<>();

    public HashMap<String, Integer> getTokenStateMap() {
        return tokenStateMap;
    }

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.account_list_activity);
        initView();
        loadData();

        bindData();

        getTokenState();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void bindData() {
        coin.setText(String.valueOf(getCoins()) + "(" + getMoney() + ")");
        userCount.setText("账号 " + mAdapter.getCount());
    }

    private int getMoney() {
        int c = getCoins();
        return c / 32;
    }

    private int getCoins() {
        UserList users = App.getUserList();
        if (users.isEmpty()) {
            return 0;
        }
        int coins = 0;
        for (int i = 0; i < users.size(); i++) {
            try {
                coins += Integer.valueOf(users.get(i).getCoins());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return coins;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscriber(tag = "refresh_user_list")
    private void refreshUserList(String msg) {
        loadData();
    }

    private void loadData() {
        UserList users = App.getUserList();
        mAdapter.changeDataSet(users);
        bindData();

    }

    private void getTokenState() {
        UserList users = mAdapter.getDataList();
        for (int i = 0; i < users.size(); i++) {
            final User user = users.get(i);
            Map<String, String> params = new HashMap<>();
            RequestUtils.sendPostRequest(Api.GETUSERINFO, user.getUid(), user.getToken(), params, new ResponseCallBack<User2>() {
                @Override
                public void onSuccess(User2 u) {
                    tokenStateMap.put(user.getUid(), 1);
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(ServiceException e) {
                    super.onFailure(e);
                    tokenStateMap.put(user.getUid(), 0);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Subscriber(tag = "getTokenState")
    private void getTokenState(final User user) {
        Map<String, String> params = new HashMap<>();
        RequestUtils.sendPostRequest(Api.GETUSERINFO, user.getUid(), user.getToken(), params, new ResponseCallBack<User2>() {
            @Override
            public void onSuccess(User2 u) {
                tokenStateMap.put(user.getUid(), 1);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                tokenStateMap.put(user.getUid(), 0);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        mAdapter = new AccountListAdapter(mContext);

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);

        mAddBtn = (Button) findViewById(R.id.addBtn);
        importBtn = (Button) findViewById(R.id.importBtn);
        activeBtn = (Button) findViewById(R.id.activeBtn);

        coin = (TextView) findViewById(R.id.coin);
        userCount = (TextView) findViewById(R.id.userCount);
        mAddBtn.setOnClickListener(this);
        importBtn.setOnClickListener(this);
        activeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mAddBtn) {
            startActivity(UserLoginActivity.createIntent(mContext));
        } else if (view == importBtn) {
            showImportDialog();
        } else if (view == activeBtn) {
            for (int i = 0; i < mAdapter.getDataList().size(); i++) {
                User u = mAdapter.getDataList().get(i);
                u.setOnline(1);
                DBManager.getInstance().updateUser(u);
            }
            mAdapter.notifyDataSetChanged();

            copyAccountToString();

            showToast("全部激活成功");
        }
    }

    private void copyAccountToString() {
        StringBuilder stringBuilder = new StringBuilder();
        UserList users = mAdapter.getDataList();
        for (int i = 0; i < users.size(); i++) {

            User u = users.get(i);
            if (u.getPhone() != null && u.getPwd() != null) {
                stringBuilder.append(u.getPhone()).append(":").append(u.getPwd());
            } else if (u.getUid() != null && u.getToken() != null) {
                stringBuilder.append(u.getUid()).append(":").append(u.getToken());
            }
            if (i != users.size() - 1) {
                stringBuilder.append(",");
            }
        }
        AppUtil.copyToClipboard(mContext, stringBuilder.toString());
    }

    private void showImportDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("输入账号数据");    //设置对话框标题
        final EditText edit = new EditText(mContext);
        edit.setInputType(InputType.TYPE_CLASS_TEXT);
        edit.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
//        edit.setPadding(AppUtil.dp2px(mContext, 5), AppUtil.dp2px(mContext, 5), AppUtil.dp2px(mContext, 5), AppUtil.dp2px(mContext, 5));
        builder.setView(edit);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(edit.getText().toString())) {
                    showToast("无账号数据");
                } else {
                    saveUserList(edit.getText().toString());
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setCancelable(true);    //设置按钮是否可以按返回键取消,false则不可以取消
        AlertDialog dialog = builder.create();  //创建对话框
        dialog.setCanceledOnTouchOutside(true); //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
        dialog.show();
    }

    private void saveUserList(String s) {
//        showProgress("导入", "账号导入中...");
        try {
            String[] account = s.split(",");
            for (int i = 0; i < account.length; i++) {
                String ss = account[i];
                String phone = ss.split(":")[0];
                String password = ss.split(":")[1];
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {
                    if (phone.length() == 11) {
                        getUserByPhone(phone, password);//phone and password
                    } else {
                        getUserByUid(phone, password);//uid and token
                    }

                } else {
                    showToast("账号数据有误");
                }
            }
        } catch (Exception e) {
            showToast("账号格式有误");
        }
    }

    private void getUserByUid(String uid, final String token) {
        Map<String, String> params = new HashMap<>();
        RequestUtils.sendPostRequest(Api.GETUSERINFO, uid, token, params, new
                ResponseCallBack<User>() {
                    @Override
                    public void onSuccess(User u) {
                        u.setToken(token);
                        if (DBManager.getInstance().isExistUser(u.getUid())) {
                            DBManager.getInstance().updateUser(u);
                        } else {
                            DBManager.getInstance().saveUser(u);
                        }

                        mHandler.removeCallbacks(loadRunnable);
                        mHandler.postDelayed(loadRunnable, 1000);
                    }

                    @Override
                    public void onFailure(ServiceException e) {
                        super.onFailure(e);
                        showToast(e.getMsg());
                    }
                });
    }

    private void getUserByPhone(final String phone, final String password) {
        Map<String, String> params = new HashMap<>();
        params.put("user", phone);
        params.put("password", password);
        RequestUtils.sendPostRequest(Api.USERNAME_PASSWOED_LOGIN, params, new ResponseCallBack<User>() {

            @Override
            public void onSuccess(User user) {
                super.onSuccess(user);
                user.setPhone(phone);
                user.setPwd(password);
                if (DBManager.getInstance().isExistUser(user.getUid())) {
                    DBManager.getInstance().updateUser(user);
                } else {
                    DBManager.getInstance().saveUser(user);
                }

                mHandler.removeCallbacks(loadRunnable);
                mHandler.postDelayed(loadRunnable, 1000);

            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                showToast(e.getMsg());
            }
        });
    }

    Runnable loadRunnable = new Runnable() {
        @Override
        public void run() {
            loadData();
        }
    };

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, AccountListActivity.class);
        return intent;
    }
}
