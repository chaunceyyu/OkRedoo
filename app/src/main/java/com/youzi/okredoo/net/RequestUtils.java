package com.youzi.okredoo.net;


import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.youzi.okredoo.Config;
import com.youzi.okredoo.util.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Map;


/**
 * 服务端请求工具
 */
public class RequestUtils {

    /**
     * @param url       请求地址
     * @param paramsMap 请求参数
     * @param callBack  回调
     */
    public static <T> void sendGetRequest(String url, Map<String, String> paramsMap, @NonNull ResponseCallBack<T> callBack) {
        sendRequest(HttpMethod.GET, url, paramsMap, callBack);
    }

    /**
     * @param url       请求地址
     * @param paramsMap 请求参数
     * @param callBack  回调
     */
    public static <T> void sendPostRequest(String url, Map<String, String> paramsMap, @NonNull ResponseCallBack<T> callBack) {
        sendRequest(HttpMethod.POST, url, paramsMap, callBack);
    }

    /**
     * @param url       请求地址
     * @param paramsMap 请求参数
     * @param callBack  回调
     */
    public static <T> void sendPostRequest(String url, String uid, String token, Map<String, String> paramsMap, @NonNull ResponseCallBack<T>
            callBack) {
        sendRequest(HttpMethod.POST, url, uid, token, paramsMap, callBack);
    }

    public static <T> void sendRequest(HttpMethod method, String url, Map<String, String> paramsMap,
                                       @NonNull final ResponseCallBack<T> callBack) {
        sendRequest(HttpMethod.POST, url, null, null, paramsMap, callBack);
    }

    public static <T> void sendRequest(HttpMethod method, String url, String uid, String token, Map<String, String> paramsMap,
                                       @NonNull final ResponseCallBack<T> callBack) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (!url.toLowerCase().startsWith("http")) {
            url = "https://api.tudouni.doubozhibo.com" + url;     //加上域名部分
        }

        final RequestParams requestParams = new RequestParams(url);
        requestParams.setConnectTimeout(5 * 1000);
//        requestParams.setCacheMaxAge(1000);
//        requestParams.setCacheSize(1024 * 1024);
        requestParams.setReadTimeout(5 * 1000);
        requestParams.setMaxRetryCount(3);
        requestParams.setCharset("utf-8");

        if (method == HttpMethod.POST) {    //直播api都是post，商城的不一定是
            requestParams.addBodyParameter("appVersion", Config.appVersion);
            requestParams.addBodyParameter("deviceId", Config.deviceId);
            requestParams.addBodyParameter("deviceModel", Config.deviceModel);
            requestParams.addBodyParameter("osType", Config.osType);
            requestParams.addBodyParameter("osVersion", Config.osVersion);
            requestParams.addBodyParameter("seqId", String.valueOf(System.currentTimeMillis()));

            if (!TextUtils.isEmpty(uid) && !TextUtils.isEmpty(token)) {
                requestParams.addBodyParameter("uid", uid);
                requestParams.addBodyParameter("token", token);
            }

        }

        //解析封装参数
        if (null != paramsMap && paramsMap.size() > 0) {
            for (String key : paramsMap.keySet()) {
                requestParams.addBodyParameter(key, paramsMap.get(key));
            }
        }

        x.http().request(method, requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                LogUtil.i("RequestUtils", "request : " + requestParams.toString());
                LogUtil.i("RequestUtils", "response: " + result);
                try {
                    T data = callBack.parse(result);
                    callBack.onSuccess(data);
                } catch (ServiceException e) {
                    callBack.onFailure(e);
                } catch (Throwable e) {
                    //这里先捕获了异常，防止被xutils捕获然后走onError回调
//                    Toast.makeText(App.sCurrActivity, "系统繁忙", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof ServiceException) {
                    //checknetwork();
                    callBack.onFailure((ServiceException) ex);
                } else if (ex instanceof ConnectException || ex instanceof SocketException) {
                    callBack.onFailure(new ServiceException("连接异常，请检查网络"));
                } else if (ex instanceof UnknownHostException || ex instanceof SocketTimeoutException
                        || ex instanceof IOException) {
                    callBack.onFailure(new ServiceException("网络连接失败，请重试"));
                } else {
                    callBack.onFailure(new ServiceException(ex.getMessage()));
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onFailure(new ServiceException(cex.getMessage()));
            }

            @Override
            public void onFinished() {

            }
        });


    }

}
