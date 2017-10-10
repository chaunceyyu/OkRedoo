package com.youzi.okredoo.util;

/**
 * Created by hjw on 17/3/22.
 */


import android.os.AsyncTask;
import android.text.TextUtils;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.yalantis.ucrop.entity.LocalMedia;
import com.youzi.okredoo.App;
import com.youzi.okredoo.model.UploadInfo;
import com.youzi.okredoo.net.Api;
import com.youzi.okredoo.net.ApiCallback;
import com.youzi.okredoo.net.RequestUtils;
import com.youzi.okredoo.net.ResponseCallBack;
import com.youzi.okredoo.net.ServiceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 阿里云上传
 */
public class UploadUtils {

    public static final String UPTYPE_IMGHEADER = "img-header";
    public static final String UPTYPE_IMGCYCLE = "img-cycle";
    public static final String UPTYPE_IMGCOMMON = "img-common";
    public static final String UPTYPE_IMGVIDEO = "img-video";
    public static final String UPTYPE_VODCYCLE = "vod-cycle";
    public static final String UPTYPE_VODIM = "vod-im";

    /**
     * 文件上传阿里云不带进度回调
     *
     * @param data
     * @param filePath
     * @return
     */
    public static String uploadFileToAliYun(UploadInfo data, String filePath) {
        try {
            String endpoint = data.getEndPoint();
            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(data
                    .getAccessid(), data.getSignature(), data.getPolicy());
            OSS oss = new OSSClient(App.getContext(), endpoint, credentialProvider);
            PutObjectRequest put = new PutObjectRequest(data.getBucket() /*+ "-trans"*/, data.getName
                    (), filePath);
            PutObjectResult putResult = oss.putObject(put);
            return data.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 文件上传阿里云带进度回调
     *
     * @param data
     * @param filePath
     * @param progressCallback
     * @return
     */
    public static String uploadFileToAliYun(UploadInfo data, String filePath, OSSProgressCallback<PutObjectRequest> progressCallback) {
        try {
            String endpoint = data.getEndPoint();
            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(data
                    .getAccessid(), data.getSignature(), data.getPolicy());
            OSS oss = new OSSClient(App.getContext(), endpoint, credentialProvider);
            PutObjectRequest put = new PutObjectRequest(data.getBucket() /*+ "-trans"*/, data.getName
                    (), filePath);
            put.setProgressCallback(progressCallback);
            PutObjectResult putResult = oss.putObject(put);
            return data.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    /**
//     * 文件上传阿里云
//     *
//     * @param data
//     * @param filePath
//     * @return
//     */
//    public static String uploadFileToAliYun(UploadInfo data, String filePath) {
//        try {
//            String endpoint = data.getEndPoint();
//            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(data
//                    .getAccessid(), data.getSignature(), data.getPolicy());
//            OSS oss = new OSSClient(App.getContext(), endpoint, credentialProvider);
//            PutObjectRequest put = new PutObjectRequest(data.getBucket(), data.getName
//                    (), filePath);
//            PutObjectResult putResult = oss.putObject(put);
//            return data.getPath();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    //---------- AliYun ----------//

    /**
     * 不带进度的文件上传
     *
     * @param filepath
     * @param apiCallback
     */
    public static void uploadFileForType(String uptype, final String filepath, final ApiCallback<String> apiCallback) {
        uploadInfo(uptype, new ResponseCallBack<UploadInfo>() {
            @Override
            public void onSuccess(final UploadInfo data) {
                super.onSuccess(data);
                (new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        return UploadUtils.uploadFileToAliYun(data, filepath);
                    }

                    @Override
                    protected void onPostExecute(String path) {
                        super.onPostExecute(path);
                        if (apiCallback != null) {
                            if (TextUtils.isEmpty(path)) {
                                apiCallback.onFailure(new ServiceException("upload aliyun failure network error"));
                            } else {
                                apiCallback.onSuccess(path);
                            }

                        }
                    }
                }).execute();
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                if (apiCallback != null) {
                    apiCallback.onFailure(e);
                }
            }
        });

    }

    /**
     * 带进度的文件上传
     *
     * @param filepath
     * @param apiCallback
     */
    public static void uploadFileForType(String uptype, final String filepath, final ApiCallback<String> apiCallback, final
    OSSProgressCallback<PutObjectRequest>
            progressCallback) {
        uploadInfo(uptype, new ResponseCallBack<UploadInfo>() {
            @Override
            public void onSuccess(final UploadInfo data) {
                super.onSuccess(data);
                (new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        return UploadUtils.uploadFileToAliYun(data, filepath, progressCallback);
                    }

                    @Override
                    protected void onPostExecute(String path) {
                        super.onPostExecute(path);
                        if (apiCallback != null) {
                            if (TextUtils.isEmpty(path)) {
                                apiCallback.onFailure(new ServiceException("upload aliyun failure network error"));
                            } else {
                                apiCallback.onSuccess(path);
                            }

                        }
                    }
                }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                if (apiCallback != null) {
                    apiCallback.onFailure(e);
                }
            }
        });

    }


    /**
     * 不带进度的视频上传 朋友圈视频
     *
     * @param filepath
     * @param apiCallback
     */
    public static void uploadVideoForCycle(final String filepath, final ApiCallback<String> apiCallback) {
        uploadFileForType(UPTYPE_VODCYCLE, filepath, apiCallback);

    }

    /**
     * 带进度的视频上传 朋友圈视频
     *
     * @param filepath
     * @param apiCallback
     * @param progressCallback
     */
    public static void uploadVideoForCycle(final String filepath, final ApiCallback<String> apiCallback, final OSSProgressCallback<PutObjectRequest>
            progressCallback) {
        uploadFileForType(UPTYPE_VODCYCLE, filepath, apiCallback, progressCallback);

    }

    /**
     * 上传图片 朋友圈图片
     *
     * @param selectMedia
     * @param apiCallback
     */
    public static void uploadImageListForCycle(final List<LocalMedia> selectMedia, final
    ApiCallback<Map<String, String>> apiCallback) {
        final Map<String, String> imgMap = new HashMap<>();
        for (final LocalMedia local : selectMedia) {
            uploadInfo(UPTYPE_IMGCYCLE, new ResponseCallBack<UploadInfo>() {
                @Override
                public void onSuccess(final UploadInfo data) {
                    super.onSuccess(data);
                    (new AsyncTask<String, String, String>() {
                        @Override
                        protected String doInBackground(String... params) {
                            return uploadFileToAliYun(data, local.getPath());
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            imgMap.put(local.getPath(), s);
                            if (imgMap.size() == selectMedia.size()) {
                                apiCallback.onSuccess(imgMap);
                            }
                        }
                    }).execute();
                }

                @Override
                public void onFailure(ServiceException e) {
                    super.onFailure(e);
                }
            });
        }

    }

    /**
     * 不带进度的视频上传 对聊视频
     *
     * @param filepath
     * @param apiCallback
     */
    public static void uploadVideoForIm(final String filepath, final ApiCallback<String> apiCallback) {
        uploadFileForType(UPTYPE_VODIM, filepath, apiCallback);

    }

    /**
     * 带进度的视频上传 对聊视频
     *
     * @param filepath
     * @param apiCallback
     * @param progressCallback
     */
    public static void uploadVideoForIm(final String filepath, final ApiCallback<String> apiCallback, final OSSProgressCallback<PutObjectRequest>
            progressCallback) {
        uploadFileForType(UPTYPE_VODIM, filepath, apiCallback, progressCallback);

    }

    /**
     * 上传图片 视频封面
     *
     * @param filepath
     * @param apiCallback
     */
    public static void uploadImageForVideo(final String filepath, final ApiCallback<String>
            apiCallback) {
        uploadFileForType(UPTYPE_IMGVIDEO, filepath, apiCallback);

    }


    /**
     * 上传图片 用户相关
     *
     * @param filepath
     * @param apiCallback
     */
    public static void uploadImageForUser(final String filepath, final ApiCallback<String> apiCallback) {
        uploadFileForType(UPTYPE_IMGHEADER, filepath, apiCallback);

    }

    /**
     * 上传图片 公共图片
     *
     * @param filepath
     * @param apiCallback
     */
    public static void uploadImageForCommon(final String filepath, final ApiCallback<String> apiCallback) {
        uploadFileForType(UPTYPE_IMGCOMMON, filepath, apiCallback);

    }


    /**
     * 上传图片 公共图片
     *
     * @param selectMedia
     * @param apiCallback
     */
    public static void uploadImageListForCommon(final List<LocalMedia> selectMedia, final
    ApiCallback<List<String>> apiCallback) {
        final List<String> imgList = new ArrayList<>();
        for (final LocalMedia local : selectMedia) {
            uploadInfo(UPTYPE_IMGCOMMON, new ResponseCallBack<UploadInfo>() {
                @Override
                public void onSuccess(final UploadInfo data) {
                    super.onSuccess(data);
                    (new AsyncTask<String, String, String>() {
                        @Override
                        protected String doInBackground(String... params) {
                            return uploadFileToAliYun(data, local.getPath());
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            imgList.add(s);
                            if (imgList.size() == selectMedia.size()) {
                                apiCallback.onSuccess(imgList);
                            }
                        }
                    }).execute();
                }
            });
        }

    }

    /**
     * 获取上传信息
     *
     * @param responseCallBack
     */
    public static void uploadInfo(String type, final ResponseCallBack<UploadInfo>
            responseCallBack) {
        Map<String, String> para1 = new HashMap<>();
        para1.put("type", type);
        RequestUtils.sendPostRequest(Api.UPLOAD_INFO, para1, new ResponseCallBack<UploadInfo>() {
            @Override
            public void onSuccess(UploadInfo data) {
                super.onSuccess(data);
                responseCallBack.onSuccess(data);
            }

            @Override
            public void onFailure(ServiceException e) {
                responseCallBack.onFailure(e);
            }

        });

    }

}
