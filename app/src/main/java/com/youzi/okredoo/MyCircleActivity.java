package com.youzi.okredoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.youzi.okredoo.adapter.MyCircleAdapter;
import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.model.Dynamic;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.net.Api;
import com.youzi.okredoo.net.RequestUtils;
import com.youzi.okredoo.net.ResponseCallBack;
import com.youzi.okredoo.net.ServiceException;
import com.youzi.okredoo.util.DateUtils;
import com.youzi.okredoo.util.LogUtil;

import org.simple.eventbus.EventBus;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人相册界面
 */
public class MyCircleActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = MyCircleActivity.class.getSimpleName();

    //Event bus tag
    public static final String MY_CIRCLE_PROGRESS_UPDATE = "MY_CIRCLE_PROGRESS_UPDATE";
    public static final String MY_CIRCLE_DYNAMIC_UPDATE = "MY_CIRCLE_DYNAMIC_UPDATE";
    public static final String MY_CIRCLE_REFRESH_DATA = "MY_CIRCLE_REFRESH_DATA";

    //    private PullToRefreshRecyclerView mPullRefreshRecyclerView;
    private RecyclerView mRecyclerView;


    private int mPage = 1;
    private MyCircleAdapter mAdapter;

    private TextView tvName;
    private ImageView ivPhoto;

    private ImageView mIvBackground;
    private String mUid = "";
    private User mUser;

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "onCreate");
        setContentView(R.layout.activity_my_circle);
        x.view().inject(this);
        EventBus.getDefault().register(this);
        mUid = getIntent().getStringExtra("uid");
        type = getIntent().getIntExtra("type", 0);
        if (mUid != null) {
            mUser = DBManager.getInstance().getUserById(mUid);
        }
        initView();
//        initEvents();
//        initUserData();

//        loadCacheData();
        loadNetData(true);
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        LogUtil.d(TAG, "onNewIntent");
//        int code = intent.getIntExtra("request", 0);
//        if (code == PublishVideoReceiver.REQUEST_UPLOAD_VIDEO_DISPLAY) {
//            loadCacheData();
////            loadNetData(true);
//        }
//    }

//    @Subscriber(tag = "MY_CIRCLE_REFRESH_DATA", mode = ThreadMode.MAIN)
//    private void refreshData(String str) {
//        LogUtil.d(TAG, "refreshData");
//        if("1".equals(str)&&videoRecordList!=null){
//            for(int i=0;i<videoRecordList.size();i++){
//                mAdapter.removeData(i+1);
//            }
//            videoRecordList.clear();
//        }
//        loadNetData(true);
//    }

//    @Subscriber(tag = "MY_CIRCLE_PROGRESS_UPDATE", mode = ThreadMode.MAIN)
//    private void updateProgress(Progress progress) {
////        LogUtil.d(TAG, "updateProgress:" + progress.getProgress() + "|path:" + progress.getId());
//        if (mAdapter != null) {
//            mAdapter.updateProgress(progress);
//        }
//    }

//    @Subscriber(tag = "MY_CIRCLE_DYNAMIC_UPDATE", mode = ThreadMode.MAIN)
//    private void updateDynamic(Dynamic dynamic) {
//        if (mAdapter != null) {
//            mAdapter.updateDynamic(dynamic);
//        }
//    }

//    private void loadCacheData() {
//
//        try {
//            if (!App.getLoginUser().getUid().equals(mUid)) {
//                return;
//            }
//            mAdapter.clear();
//            File dir = getExternalCacheDir();
//            File cacheDir = new File(dir, "CacheData" + File.separator + App.getLoginUser().getUid());
//            File cacheFile = new File(cacheDir, "my_circle_json_data");
//
//            if (cacheFile.exists()) {
//                FileInputStream fis = new FileInputStream(cacheFile);
//                byte[] data = new byte[fis.available()];
//                fis.read(data);
//                fis.close();
//
//                String json = new String(data);
//
//                List<Dynamic> list = new Gson().fromJson(json, new TypeToken<List<Dynamic>>() {
//                }.getType());
//
//                if (list != null) {
//                    List<Dynamic> ls = resetData(list, true);
//                    mAdapter.addData(ls);
//                }
//
//                //加那个上传中视频数据
////                loadUploadData();
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

//    private void saveToCache(List<Dynamic> data) {
//        if (!App.getLoginUser().getUid().equals(mUid)) {
//            return;
//        }
//        try {
//            File dir = getExternalCacheDir();
//            File cacheDir = new File(dir, "CacheData" + File.separator + App.getLoginUser().getUid());
//            if (!cacheDir.exists()) {
//                cacheDir.mkdirs();
//            }
//
//            File cacheFile = new File(cacheDir, "my_circle_json_data");
//
//            FileOutputStream fos = new FileOutputStream(cacheFile);
//            fos.write(new Gson().toJson(data).getBytes());
//
//            fos.flush();
//            fos.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    List<VideoRecord> videoRecordList;
//    private void loadUploadData() {
//        VideoRecordDao videoRecordDao = DBManager.getInstance().getDaoSession().getVideoRecordDao();
//        videoRecordList = videoRecordDao.loadAll();
//        if (!videoRecordList.isEmpty()) {
//            addToDynamicList(videoRecordList);
////            sortList();
////            mAdapter.notifyDataSetChanged();
//
//        }else{
//          mAdapter.notifyDataSetChanged();
//        }
//    }

    private void sortList() {
//        Collections.sort(mAdapter.getDynamicList(), new TimeComparator());
    }

//    private void addToDynamicList(List<VideoRecord> videoRecordList) {
//        for (int i = 0; i < videoRecordList.size(); i++) {
//            VideoRecord videoRecord = videoRecordList.get(i);
//            Dynamic dynamic = new Dynamic();
//            dynamic.setVideoCover(videoRecord.getThumbPath());
//            dynamic.setContent(videoRecord.getContent());
//            dynamic.setDate(videoRecord.getDate());
//            dynamic.setVideoURL(videoRecord.getVideoPath());
//            dynamic.setLocalType(Dynamic.LOCAL_TYPE_UPLOAD_VIDEO);
//            dynamic.setType(String.valueOf(Dynamic.TYPE_VIDEO));
//            dynamic.setViewStatus(String.valueOf(videoRecord.getViewStatus()));
//            mAdapter.addData(1, dynamic);
//        }
//    }

//    /**
//     * 按时间排序
//     */
//    public class TimeComparator implements java.util.Comparator<Dynamic> {
//
//        @Override
//        public int compare(Dynamic dy1, Dynamic dy2) {
//            try {
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date d1 = sdf.parse(dy1.getDate());
//                Date d2 = sdf.parse(dy2.getDate());
//
//
//                if (d1.before(d2)) {
//                    return 1;
//                } else {
//                    return -1;
//                }
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//                return 0;
//            }
//        }
//    }

//    private void initUserData() {
//        if (StringUtil.isNotNull(mUid)) {
//            CommonHelper.userInfo(mUid, new ResponseCallBack<OtherUserInfo>() {
//                @Override
//                public void onSuccess(OtherUserInfo data) {
//                    super.onSuccess(data);
//                    tvName.setText(data.getNickName());
//                    ImageUtils.display150(ivPhoto, data.getPhoto());
//
//                    if (!StringUtil.isEmpty(data.getBackground())) {
//                        ImageUtils.displayImage(mIvBackground, data.getBackground());
//
//                    } else {
//                        mIvBackground.setImageResource(R.mipmap.circle_bg);
//                    }
//                }
//
//                @Override
//                public void onFailure(ServiceException e) {
//                    super.onFailure(e);
//                }
//            });
//
//        }
//    }


//    private void initEvents() {
//        mPullRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//                        loadNetData(true);
//                    }
//                }, 10);
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//                        loadNetData(false);
//                    }
//                }, 10);
//            }
//        });
//    }


    private void loadNetData(final boolean refresh) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("page", String.valueOf(refresh ? 1 : mPage + 1));
        params.put("sid", mUid);
        params.put("pageSize", "100");
        RequestUtils.sendPostRequest(Api.DYNAMIC_USER_LIST, mUser.getUid(), mUser.getToken(), params, new ResponseCallBack<List<Dynamic>>() {
            @Override
            public void onSuccess(List<Dynamic> data) {
                super.onSuccess(data);
//                mPullRefreshRecyclerView.onRefreshComplete();
//                if (refresh) {
//                    disTime = "";
//                    mAdapter.clear();
//                    mPage = 1;                   //如果刷新，无论是否有数据，都置为1
////                    saveToCache(data);
//                }
//                if (data != null && !data.isEmpty() && !refresh) {
//                    mPage++;                    //上拉时拉到有数据才+1
//                }
                List<Dynamic> ls = resetData(data != null ? data : new ArrayList<Dynamic>(), refresh);
                mAdapter.addData(ls);

                //加那个上传中视频数据
//                if (refresh) {
//                    loadUploadData();
//                }
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
//                mPullRefreshRecyclerView.onRefreshComplete();
                showToast(e.getMsg());
            }
        });

    }


    String disTime = "";

    private List<Dynamic> resetData(List<Dynamic> data, boolean refresh) {
        List<Dynamic> ls = new ArrayList<>();

        for (Dynamic dn : data) {
            if (!TextUtils.isEmpty(dn.getDate())) {
                String strTime = DateUtils.formatDisplayTime2(dn.getDate(), "yyyy-MM-dd HH:mm:ss");
                if (strTime.equals(disTime)) {
                    dn.setMarginTop(false);
                    dn.setMonth("");
                    dn.setDay("");
                    dn.setLastDate("");
                } else {
                    disTime = strTime;
                    dn.setMarginTop(true);
                    if (strTime.contains(",")) {
                        String[] md = strTime.split(",");
                        dn.setMonth(md[0]);
                        dn.setDay(md[1]);
                        dn.setLastDate("");
                    } else {
                        dn.setMonth("");
                        dn.setDay("");
                        dn.setLastDate(disTime);
                    }

                }
            }

        }

//        if (refresh && mUid.equals(App.getLoginUser().getUid())) {
//            if (data.size() > 0) {
//                String d1 = data.get(0).getLastDate();
//                if (d1.equals("今天")) {
//                    data.get(0).setLastDate("");
//                    data.get(0).setMarginTop(false);
//                }
//            }
//            Dynamic d = new Dynamic();
//            d.setLastDate("今天");
//            d.setType("10");
//            ls.add(0, d);
//            ls.addAll(data);
//
//        } else {
        ls = data;
//        }
        return ls;
    }


    private void initView() {
//        commonTitle.setTitleText(getResources().getString(R.string.circle));
//        commonTitle.setActivity(this);
//        commonTitle.imgShow(View.VISIBLE);
//        if (App.getLoginUser().getUid().equals(mUid)) {
//            commonTitle.getImg().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    CommonHelper.showDynamicPop(MyCircleActivity.this);
//                }
//            });
//            commonTitle.imgDrawable(getResources().getDrawable(R.mipmap.icon_add_circle), 30);
//        }

//        mPullRefreshRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.recyclerView_circle);
//        mPullRefreshRecyclerView.setHeaderLayout(new TudouniHeadLoadingView(this));
//        mPullRefreshRecyclerView.setFooterLayout(new TudouniFooterLoadingView(this));


        initRecyclerView();
    }

    private void initRecyclerView() {
//        mRecyclerView = mPullRefreshRecyclerView.getRefreshableView();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_circle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new MyCircleAdapter(this, mUser);
//        SmartRecyclerAdapter smartRecyclerAdapter = new SmartRecyclerAdapter(mAdapter);

//        View header = this.getLayoutInflater().inflate(R.layout.activity_my_circle_head, null);

//        tvName = (TextView) header.findViewById(R.id.tvName);
//        ivPhoto = (ImageView) header.findViewById(R.id.ivPhoto);
//        ivPhoto.setOnClickListener(this);

        //背景
//        mIvBackground = (ImageView) header.findViewById(R.id.iv_background);

//        smartRecyclerAdapter.setHeaderView(header);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new MyCircleAdapter.RecyclerViewListener() {
            @Override
            public void onItemClick(View view, Object data) {
                if (type == 1) {
                    Dynamic dynamic = (Dynamic) data;
                    Intent intent = new Intent();
                    intent.putExtra("dynamic", dynamic);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        if (v == ivPhoto) {
//            startActivity(UserCenterActivity.createIntent(getBaseContext(), mUid));
        }
    }

    /**
     * @param context
     * @param uid
     * @return
     */
    public static Intent createIntent(Context context, String uid, int type) {
        Intent intent = new Intent(context, MyCircleActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("type", type);
        return intent;
    }
}
