package com.youzi.okredoo;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.view.webview.WVJBWebViewClient;

import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import static com.youzi.okredoo.R.id.uid;


@SuppressLint("SetJavaScriptEnabled")
public class H5Activity extends BaseActivity {

    public static final String support = "support,getUserInfo,login,closeWebView,getDeviceInfo," +
            "copy,setShareInfo,jumpPage,getUserAgent,setBgColor,uploadCallback,loginSuccess,payCallback";

    String url;


    @ViewInject(R.id.webView)
    WebView webview;
//    @ViewInject(R.id.progress)
//    ProgressBar progress;
    private WVJBWebViewClient webViewClient;

    private User mUser;

    private Handler payHandler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 111: {
                    if (msg.obj != null) {
                        Toast.makeText(H5Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case 222: {
                    if (msg.obj != null) {
                        Toast.makeText(H5Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    break;
                }
            }
        }

        ;
    };
    private RelativeLayout comm_head;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);
        x.view().inject(this);
        url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        String uid = getIntent().getStringExtra("uid");
        if (uid != null) {
            mUser = DBManager.getInstance().getUserById(uid);
        }
        comm_head = (RelativeLayout) findViewById(R.id.comm_head);

        WebChromeClient webChromeClient = new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress == 100) {
//                    progress.setVisibility(View.GONE);
//                } else {
//                    if (progress.getVisibility() == View.GONE)
//                        progress.setVisibility(View.VISIBLE);
//                    progress.setProgress(newProgress);
//                }
                super.onProgressChanged(view, newProgress);
            }
        };
//        storage
        setstorge(webview);
        webview.setWebChromeClient(webChromeClient);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setAllowFileAccess(true);
        webViewClient = new MyWebViewClient(webview);
        webViewClient.enableLogging();
        webview.setWebViewClient(webViewClient);

        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);       //允许调试
        }

        webview.loadUrl(getIntent().getStringExtra("url"));
    }

    private void setstorge(WebView view) {
        view.getSettings().setJavaScriptEnabled(true);
        // 开启DOM缓存。
        view.getSettings().setDomStorageEnabled(true);
        view.getSettings().setDatabaseEnabled(true);
        view.getSettings().setDatabasePath(this.getApplicationContext().getCacheDir().getAbsolutePath());

    }

    class MyWebViewClient extends WVJBWebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //处理代码

//            if (url.startsWith("tudouni://tudouni/home")) {
//                ForwardUtils.target(H5Activity.this, Constant.MAIN);
//                return true;
//            }
//
//            if (url.startsWith("doubozhibo://")) {
//                ForwardUtils.target(H5Activity.this, url);
//                return true;
//            }

            return super.shouldOverrideUrlLoading(view, url);
        }

        public MyWebViewClient(WebView webView) {

            super(webView, new WVJBHandler() {

                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    callback.callback("Response for message from ObjC!");
                }
            });

            enableLogging();

            //是否支持该方法
            registerHandler("support", new WVJBHandler() {

                @Override
                public void request(Object data, WVJBResponseCallback callback) {

                    if (data != null && H5Activity.support.indexOf(data.toString()) != -1) {
                        callback.callback("{\"status\":\"yes\"}");
                    }
                }
            });
            //获得用户信息
            registerHandler("getUserInfo", new WVJBHandler() {

                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    User user = mUser;
                    if (user != null) {
                        String js = new Gson().toJson(user);
                        callback.callback(js);
                    } else {
                        callback.callback("");
                    }
                }
            });
            //获得UserAgent
            registerHandler("getUserAgent", new WVJBHandler() {

                @Override
                public void request(Object data, WVJBResponseCallback callback) {

                }
            });
            //登录
            registerHandler("login", new WVJBHandler() {

                @Override
                public void request(Object data, WVJBResponseCallback callback) {

                }
            });
            //关闭webview
            registerHandler("closeWebView", new WVJBHandler() {

                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    H5Activity.this.onBackPressed();
                    H5Activity.this.finish();
                    callback.callback("{\"status\":\"yes\"}");
                }
            });
            //获得设备信息
            registerHandler("getDeviceInfo", new WVJBHandler() {

                @Override
                public void request(Object data, WVJBResponseCallback callback) {

                }
            });

            //拷贝
            registerHandler("copy", new WVJBHandler() {

                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    if (data != null) {
                        try {
                            JSONObject ja = new JSONObject(data.toString());
                            ClipboardManager clip = (ClipboardManager) H5Activity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                            clip.setText(ja.getString("content"));

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    callback.callback("{\"status\":\"yes\"}");
                }
            });

            //分享
            registerHandler("share", new WVJBHandler() {

                @Override
                public void request(Object data, WVJBResponseCallback callback) {

//                    JSONObject ja = null;
//                    try {
//                        ja = new JSONObject(data.toString());
////                        {"url":"","title","","content":"","img":""}
//                        String url = ja.getString("url");
//                        String title = ja.getString("title");
//                        String content = ja.getString("content");
//                        String img = ja.getString("img");
//                        Share share = new Share(title, content, img, url);
//                        ShareWindow_v2 p = new ShareWindow_v2(H5Activity.this, null, share, null, null);
//                        p.show(H5Activity.this);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }


                }
            });

            //跳转URL
            registerHandler("jumpPage", new WVJBHandler() {

                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    if (data != null) {
                        try {
                            JSONObject ja = new JSONObject(data.toString());
                            String url = ja.getString("url");
                            startActivity(H5Activity.newIntent(mContext, url, ""));

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    callback.callback("{\"status\":\"yes\"}");
                }
            });

            //设置背景颜色
            registerHandler("setBgColor", new WVJBHandler() {

                @Override
                public void request(Object data, WVJBResponseCallback callback) {

                    try {

                        JSONObject ja = new JSONObject(data.toString());

                        int r = ja.getInt("r");

                        int g = ja.getInt("g");

                        int b = ja.getInt("b");

                        webview.setBackgroundColor(Color.argb(1, r, g, b));

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

//            //设置背景颜色
//            registerHandler("aliauth", new WVJBHandler() {
//
//                @Override
//                public void request(Object data, WVJBResponseCallback callback) {
//                    startAuth();
//                }
//            });

            //设置背景颜色
            registerHandler("privateChat", new WVJBHandler() {

                @Override
                public void request(Object data, WVJBResponseCallback callback) {
//                    JSONObject ja = null;
//                    try {
//                        ja = new JSONObject(data.toString());
//                        int r = ja.getInt("r");
//
//                        int g = ja.getInt("g");
//
//                        int b = ja.getInt("b");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    if (data != null) {
//                        try {
//
//                            String uid = ja.getString("uid");
//                            String nickName = ja.getString("nickName");
//                            if (!TextUtils.isEmpty(uid) && !TextUtils.isEmpty(nickName)) {
//
//                                RongIM.getInstance().startPrivateChat(H5Activity.this, uid, nickName);
//                            }
//
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
                }
            });


        }
    }

//    private void startAuth() {
//        H5Activity.this.loading("认证中...");
//        RequestUtils.sendPostRequest(Api.ALIAUTH, null, new ResponseCallBack<AliAuth>() {
//
//            @Override
//            public void onSuccess(final AliAuth data) {
//                super.onSuccess(data);
//                Runnable authRunnable = new Runnable() {
//
//                    @Override
//                    public void run() {
//                        Log.e("auth", "start");
//                        AuthTask authTask = new AuthTask(H5Activity.this);
//                        Map<String, String> result = authTask.authV2(data.getPayInfo(), true);
//                        AuthResult authResult = new AuthResult((Map<String, String>) result, true);
//                        String resultStatus = authResult.getResultStatus();
//                        if ("9000".equals(resultStatus) && "200".equals(authResult.getResultCode())) {
//                            Map<String, String> param = new HashMap<>();
//                            param.put("authCode", authResult.getAuthCode());
//                            Log.e("auth", "-----" + authResult.getAuthCode());
//                            RequestUtils.sendPostRequest(Api.DOAUTH, param, new ResponseCallBack<AliAuth>() {
//
//                                @Override
//                                public void onSuccess(final AliAuth data) {
//                                    super.onSuccess(data);
//                                    Message msg = new Message();
//                                    msg.what = 222;
//                                    msg.obj = "认证成功";
//                                    payHandler.sendMessage(msg);
//                                }
//
//                                @Override
//                                public void onFailure(ServiceException e) {
//                                    super.onFailure(e);
//                                    Message msg = new Message();
//                                    msg.what = 111;
//                                    msg.obj = e.getMsg();
//                                    payHandler.sendMessage(msg);
//                                }
//                            });
//                        } else {
//                            Message msg = new Message();
//                            msg.what = 111;
//                            if ("4000".equals(resultStatus)) {
//                                msg.obj = "认证失败,请检查是否安装支付宝";
//                            } else {
//                                msg.obj = "认证失败";
//                            }
//                            payHandler.sendMessage(msg);
//                        }
//                    }
//                };
//                // 必须异步调用
//                Thread authThread = new Thread(authRunnable);
//                authThread.start();
//            }
//
//            @Override
//            public void onFailure(ServiceException e) {
//                super.onFailure(e);
//                H5Activity.this.dissLoad();
//                CommonHelper.showTip(H5Activity.this, e.getMsg());
//            }
//        });

//    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //页面激活
        webViewClient.callHandler("reActive");
    }

    public static Intent newIntent(Context context, String url, String uid) {
        Intent intent = new Intent(context, H5Activity.class);
        intent.putExtra("url", url);
        intent.putExtra("uid", uid);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((ViewGroup) webview.getParent()).removeView(webview);
        webview.destroy();
    }
}


