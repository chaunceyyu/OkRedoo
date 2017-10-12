package com.youzi.okredoo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.FunctionOptions;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;
import com.youzi.okredoo.adapter.DynamicImageAddImageAdapter;
import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.model.Dynamic;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.net.Api;
import com.youzi.okredoo.net.ApiCallback;
import com.youzi.okredoo.net.RequestUtils;
import com.youzi.okredoo.net.ResponseCallBack;
import com.youzi.okredoo.net.ServiceException;
import com.youzi.okredoo.util.FullyGridLayoutManager;
import com.youzi.okredoo.util.LogUtil;
import com.youzi.okredoo.util.StringUtil;
import com.youzi.okredoo.util.UploadUtils;

import org.simple.eventbus.EventBus;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 发送动态图片界面
 */
public class SendImageDynamicActivity extends BaseActivity {

    private static final int REQUEST_CODE_SELECT_IMAGE = 1;

    @ViewInject(R.id.recycler)
    private RecyclerView recyclerView;

    @ViewInject(R.id.llOpen)
    LinearLayout llOpen;
    @ViewInject(R.id.llFriend)
    LinearLayout llFriend;
    @ViewInject(R.id.llSimi)
    LinearLayout llSimi;

    @ViewInject(R.id.ivOpen)
    ImageView ivOpen;
    @ViewInject(R.id.ivFriend)
    ImageView ivFriend;
    @ViewInject(R.id.ivSimi)
    ImageView ivSimi;
    private int viewStatus = 0;

    @ViewInject(R.id.etContent)
    EditText etContent;

    @ViewInject(R.id.sendBtn)
    Button sendBtn;

    private DynamicImageAddImageAdapter adapter;

    private List<LocalMedia> selectMedia = new ArrayList<>();
    private TextView num;

    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_dynamic);
        x.view().inject(this);
        String mUid = getIntent().getStringExtra("uid");
        if (mUid != null) {
            mUser = DBManager.getInstance().getUserById(mUid);
        }

        EventBus.getDefault().register(this);

        initView();
        choiseImages();
    }

    private void initView() {
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int i = s.length();
                num.setText(i + "/" + 200);
                //num.setText(i + "/" + 140);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        num = (TextView) findViewById(R.id.num);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImagesAndSave();
            }
        });

        FullyGridLayoutManager manager = new FullyGridLayoutManager(SendImageDynamicActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new DynamicImageAddImageAdapter(SendImageDynamicActivity.this, onAddPicClickListener);
        adapter.setSelectMax(9);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new DynamicImageAddImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // 这里可预览图片
                PictureConfig.getInstance().externalPicturePreview(SendImageDynamicActivity.this, position, selectMedia);
            }
        });

        llOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeViewStatus(0, ivOpen, ivFriend, ivSimi);
            }
        });
        llFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeViewStatus(1, ivFriend, ivOpen, ivSimi);
            }
        });
        llSimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeViewStatus(2, ivSimi, ivOpen, ivFriend);
            }
        });

    }

    private void uploadImagesAndSave() {
        if (selectMedia.size() < 1) {
            showToast("请选择图片");
            return;
        }
        showProgress("保存中", "保存中...");
        final List<String> rate = new ArrayList<String>();

        UploadUtils.uploadImageListForCycle(selectMedia, new ApiCallback<Map<String, String>>() {

            @Override
            public void onSuccess(Map<String, String> data) {
                List<String> imageList = comImageList(selectMedia, data);
                selectMedia.clear();
                saveDynamic(imageList, rate);
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
            }
        });

    }

    private List<String> comImageList(List<LocalMedia> selectMedia, Map<String, String> data) {
        List<String> list = new ArrayList<>();
        for (LocalMedia local : selectMedia) {
            list.add(data.get(local.getPath()));
        }
        return list;
    }

    private void saveDynamic(List<String> imgPath, List<String> rate) {

        String images = "";
        String rates = "";
        List<String> rateList = new ArrayList<>();
        if (null != imgPath && imgPath.size() > 0) {
            images = StringUtil.listToString(imgPath);
            for (String img : imgPath) {
                rateList.add("1");
            }
            rates = StringUtil.listToString(rateList);
        }

        String type = "";
        String content = etContent.getText().toString();
        if (images.length() > 0) {
            type = String.valueOf(Dynamic.TYPE_IMAGE);

        } else {
            type = "0";
            //文字动态
            if (TextUtils.isEmpty(content)) {
                showToast("请输入动态内容");
                return;
            }
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("address", "");
        map.put("content", content);
        map.put("images", images);
        map.put("lat", "");
        map.put("lon", "");
        map.put("rate", rates);
        map.put("type", type);
        map.put("video", "");
        map.put("viewStatus", String.valueOf(viewStatus));
        RequestUtils.sendPostRequest(Api.PUSH_DYNAMIC, mUser.getUid(), mUser.getToken(), map, new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                closeProgress();
                showToast("发布成功");
                finish();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        EventBus.getDefault().post("", "refreshCycList");
                    }
                }, 1000);
            }

            @Override
            public void onFailure(ServiceException e) {
                closeProgress();
            }
        });
    }

    private void changeViewStatus(int status, ImageView iv1, ImageView iv2, ImageView iv3) {
        viewStatus = status;
        iv1.setVisibility(View.VISIBLE);
        iv2.setVisibility(View.GONE);
        iv3.setVisibility(View.GONE);
    }


    /**
     * 删除图片回调接口
     */
    private DynamicImageAddImageAdapter.onAddPicClickListener onAddPicClickListener = new DynamicImageAddImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    choiseImages();
                    break;
                case 1:
                    // 删除图片
                    selectMedia.remove(position);
                    adapter.notifyItemRemoved(position);
                    break;
            }
        }
    };

    private void choiseImages() {
        FunctionOptions options = new FunctionOptions.Builder()
                .setType(FunctionConfig.TYPE_IMAGE)
                .setCompress(true)
                .setEnablePixelCompress(true)
                .setEnableQualityCompress(true)
                .setMaxSelectNum(9)
                .setSelectMode(FunctionConfig.MODE_MULTIPLE)
                .setShowCamera(true)
                .setEnablePreview(false)
                .setEnableCrop(false)
                .setCheckNumMode(false)
                .setCompressQuality(100)
                .setImageSpanCount(3)
                .setSelectMedia(selectMedia)
                .setCompressFlag(1)
                .setCompressW(0)
                .setCompressH(0).create();
        PictureConfig.getInstance().init(options);
        PictureConfig.getInstance().openPhoto(SendImageDynamicActivity.this, resultCallback);

//        Intent intent = new Intent(getBaseContext(), PictureSelectorActivity.class);
//        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SELECT_IMAGE:
                if (resultCode == RESULT_OK) {
                    boolean sendOrigin = data.getBooleanExtra("sendOrigin", false);
                    ArrayList<Uri> imageList = data.getParcelableArrayListExtra("android.intent.extra.RETURN_RESULT");
                    imageList.size();
                }
                break;
        }
    }

    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            selectMedia = resultList;
            if (selectMedia != null) {
                LogUtil.i("callBack_result", selectMedia.size() + "");
                adapter.setList(selectMedia);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            if (media != null) {
                selectMedia.add(media);
                adapter.setList(selectMedia);
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public static Intent createIntent(Context context, String uid) {
        Intent intent = new Intent(context, SendImageDynamicActivity.class);
        intent.putExtra("uid", uid);
        return intent;
    }
}
