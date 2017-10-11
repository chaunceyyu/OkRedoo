package com.youzi.okredoo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.FunctionOptions;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;
import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.net.Api;
import com.youzi.okredoo.net.ApiCallback;
import com.youzi.okredoo.net.RequestUtils;
import com.youzi.okredoo.net.ResponseCallBack;
import com.youzi.okredoo.net.ServiceException;
import com.youzi.okredoo.util.LogUtil;
import com.youzi.okredoo.util.UploadUtils;

import org.simple.eventbus.EventBus;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjiajie on 2017/10/10.
 */

public class UserInfoEditActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "UserInfoActivity";

    @ViewInject(R.id.main)
    LinearLayout main;

    @ViewInject(R.id.llUser)
    LinearLayout llUser;
    @ViewInject(R.id.llName)
    LinearLayout llName;
    @ViewInject(R.id.llSex)
    LinearLayout llSex;
    @ViewInject(R.id.llId)
    LinearLayout llId;
    @ViewInject(R.id.llSign)
    LinearLayout llSign;
    @ViewInject(R.id.llBirthday)
    LinearLayout llBirthday;
    @ViewInject(R.id.llCity)
    LinearLayout llCity;

    @ViewInject(R.id.etName)
    EditText etName;
    @ViewInject(R.id.etSign)
    EditText etSign;

    @ViewInject(R.id.tvSex)
    TextView tvSex;
    @ViewInject(R.id.tvCity)
    TextView tvCity;
    @ViewInject(R.id.tvId)
    TextView tvId;
    @ViewInject(R.id.tvBirthday)
    TextView tvBirthday;

    @ViewInject(R.id.ivPhoto)
    ImageView ivPhoto;

    private String photoPath;
    private String photoUrl;


    private int selectMode = FunctionConfig.MODE_SINGLE;
    private int maxSelectNum = 9;// 图片最大可选数量
    private ImageButton minus, plus;
    private EditText select_num;
    private EditText et_w, et_h, et_compress_width, et_compress_height;
    private LinearLayout ll_luban_wh;
    private boolean isShow = true;
    private int selectType = FunctionConfig.TYPE_IMAGE;
    private int copyMode = FunctionConfig.CROP_MODEL_1_1;
    private boolean enablePreview = true;
    private boolean isPreviewVideo = true;
    private boolean enableCrop = true;
    private boolean theme = false;
    private boolean selectImageType = false;
    private int cropW = 640;
    private int cropH = 640;
    private int compressW = 0;
    private int compressH = 0;
    private boolean isCompress = false;
    private boolean isCheckNumMode = false;
    private int compressFlag = 1;// 1 系统自带压缩 2 luban压缩
    private List<LocalMedia> selectMedia = new ArrayList<>();
    private Context mContext;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit_activity);
        x.view().inject(this);
        mContext = this;
        String uid = getIntent().getStringExtra("uid");
        user = DBManager.getInstance().getUserById(uid);
        initView();
        initData();

    }

    private void initData() {
        Glide.with(this).load(user.getPhoto()).into(ivPhoto);
        etName.setText(user.getNickName());
        //昵称光标右边
        etName.setSelection(etName.length());
        if (user.getCity() == null) {
            tvCity.setText("难道在火星");
        } else {
            tvCity.setText(user.getCity());
        }
        String sign = user.getSignature();
        if (!TextUtils.isEmpty(sign)) {
            etSign.setText(sign);
        }
        //个签光标右边
        etSign.setSelection(etSign.length());
        tvId.setText(user.getUnumber());
        tvBirthday.setText(user.getBirthday());
        tvSex.setText("0".equals(user.getSex()) ? "女" : "男");

        etSign.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    String sign = etSign.getText().toString();
                    if ("这个家伙有点懒~".equals(sign)) {
                        etSign.setText("");
                    }
                }
            }
        });

        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etName.setText("");
                }
            }
        });

        photoUrl = user.getPhoto();

    }


    private void initView() {

        llUser.setOnClickListener(this);
        llSex.setOnClickListener(this);
        llCity.setOnClickListener(this);
        llBirthday.setOnClickListener(this);

        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
            }
        });

    }

    private void saveUserInfo() {
        if (TextUtils.isEmpty(etName.getText().toString())) {
            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String sign = etSign.getText().toString();
        if (null == sign || "".equals(sign)) {
            sign = "这个家伙有点懒~";
        }
//        user = new User();

        user.setBirthday(tvBirthday.getText().toString());
        user.setCity(tvCity.getText().toString());
        user.setNickName(etName.getText().toString());
        user.setSex(tvSex.getText().toString().equals("男") ? "1" : "0");
        user.setSignature(sign);
        user.setPhoto(photoUrl);

        Map<String, String> params = new HashMap<>();
        params.put("birthday", tvBirthday.getText().toString());
        params.put("city", tvCity.getText().toString());
        params.put("nickName", etName.getText().toString());
        if (!TextUtils.isEmpty(photoUrl)) {
            params.put("photo", photoUrl);
        }
        params.put("sex", tvSex.getText().toString().equals("男") ? "1" : "0");
        params.put("singtrue", sign);
        RequestUtils.sendPostRequest(Api.SET_INFO, user.getUid(), user.getToken(), params, new ResponseCallBack<Object>() {

            @Override
            public void onSuccess(Object data) {
                super.onSuccess(data);
                showToast("保存成功");
                DBManager.getInstance().updateUser(user);
                finish();
                EventBus.getDefault().post("", "refresh_user_list");
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                showToast(e.getMsg());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llSex:
//                CommonUtil.hideKeyBoard(this, etName);
                upSex();
                break;
//            case R.id.tvLogout:
//                App.logout();
//                this.finish();
//                ForwardUtils.target(UserInfoActivity.this, Constant.LOGIN);
//                break;
            case R.id.llUser:
                initDialogOpenAvatar();
                break;
            case R.id.llBirthday:
//                DatePickDialog dialog = new DatePickDialog(this);
//                dialog.setYearLimt(70);
//                dialog.setTitle("选择时间");
//                dialog.setType(DateType.TYPE_YMD);
//                dialog.setStartDate(new Date(System.currentTimeMillis() - 525600000));
//                dialog.setMessageFormat("yyyy-MM-dd");
//                dialog.setOnChangeLisener(null);
//                dialog.setOnSureLisener(new OnSureLisener() {
//                    @Override
//                    public void onSure(Date date) {
//                        tvBirthday.setText(DateUtils.dateToStr(date, "yyyy-MM-dd"));
//                    }
//                });
//                dialog.show();
                break;
            case R.id.llCity:
//                CityPicker cityPicker = new CityPicker.Builder(this)
//                        .textSize(16)
//                        .title("地址选择")
//                        .titleBackgroundColor("#ffffff")
//                        .titleTextColor("#000000")
//                        .confirTextColor("#000000")
//                        .cancelTextColor("#000000")
//                        .province("北京")
//                        .city("北京")
//                        .district("北京")
//                        .textColor(Color.parseColor("#000000"))
//                        .provinceCyclic(true)
//                        .cityCyclic(false)
//                        .districtCyclic(false)
//                        .visibleItemsCount(7)
//                        .itemPadding(10)
//                        .onlyShowProvinceAndCity(true)
//                        .build();
//                cityPicker.show();
//
////监听方法，获取选择结果
//                cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
//                    @Override
//                    public void onSelected(String... citySelected) {
//                        //城市
//                        /*** @@@@ modify by 24020 at 2017/3/28 21:58@@@@@*****/
////                        String city = citySelected[0] + citySelected[1];
//                        String city = citySelected[1];
//                        /*** @@@@ modify by 24020 at 2017/3/28 21:58@@@@@*****/
//                        tvCity.setText(city);
//                    }
//                });
                break;

        }
    }

    //选择性别。
    private void upSex() {
//        DialogChooseSex dialogChoosePick = new DialogChooseSex(UserInfoActivity.this, new DialogChooseSex.Dialogcallback() {
//            @Override
//            public void pickWeightResult(String sex) {
//
//                tvSex.setText(sex);
//            }
//        });
//        dialogChoosePick.show();
    }

//    private boolean Check_NUllOrEmp() {
//
//        if (StringUtil.isNullOrEmpty(tvBirthday.getText().toString())) {
//            return true;
//        }
//        if (StringUtil.isNullOrEmpty(tvCity.getText().toString())) {
//            return true;
//        }
//        if (StringUtil.isNullOrEmpty(tvSex.getText().toString())) {
//            return true;
//        }
//        if (StringUtil.isNullOrEmpty(etName.getText().toString())) {
//            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        if (StringUtil.isNullOrEmpty(etSign.getText().toString())) {
//            Toast.makeText(this, "签名不能为空", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//
//        return false;
//    }


    private void initDialogOpenAvatar() {
        checkPermission();
        FunctionOptions options = new FunctionOptions.Builder()
                .setType(selectType)
                .setCropMode(copyMode)
                .setCompress(true)
                .setEnablePixelCompress(true)
                .setEnableQualityCompress(true)
                .setMaxSelectNum(1)
                .setSelectMode(selectMode)
                .setShowCamera(isShow)
                .setEnablePreview(enablePreview)
                .setEnableCrop(enableCrop)
                .setCropW(cropW)
                .setCropH(cropH)
                .setCheckNumMode(isCheckNumMode)
                .setCompressQuality(100)
                .setImageSpanCount(3)
                .setSelectMedia(selectMedia)
                .setCompressFlag(1)
                .setCompressW(compressW)
                .setCompressH(compressH)
                .create();


        // 先初始化参数配置，在启动相册
        PictureConfig.getInstance().init(options);
        PictureConfig.getInstance().openPhoto(this, resultCallback2);
    }


    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback2 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            selectMedia = resultList;
            if (selectMedia != null) {
                LogUtil.i("callBack_result", selectMedia.size() + "");
                photoPath = selectMedia.get(0).getCompressPath();
                LogUtil.i("malegebi", photoPath);
                showProgress("上传中", "头像上传中");
                UploadUtils.uploadImageForUser(photoPath, new ApiCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        photoUrl = data;
                        Glide.with(mContext).load(photoUrl).into(ivPhoto);
                        closeProgress();
                    }

                    @Override
                    public void onFailure(ServiceException e) {
                        super.onFailure(e);
                        closeProgress();
                    }
                });

            }
        }

        @Override
        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            if (selectMedia != null) {
                photoPath = media.getCompressPath();
                showProgress("上传中", "头像上传中");
                UploadUtils.uploadImageForUser(photoPath, new ApiCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        photoUrl = data;
                        Glide.with(mContext).load(photoUrl).into(ivPhoto);
                        closeProgress();
                    }

                    @Override
                    public void onFailure(ServiceException e) {
                        super.onFailure(e);
                        closeProgress();
                    }
                });

            }
        }
    };


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int write = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (write != PackageManager.PERMISSION_GRANTED || read != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 300);
            } else {
                String name = "CrashDirectory";
                File file1 = new File(Environment.getExternalStorageDirectory(), name);
                if (file1.mkdirs()) {
                    LogUtil.i("wytings", "permission -------------> " + file1.getAbsolutePath());
                } else {
                    LogUtil.i("wytings", "permission -------------fail to make file ");
                }
            }
        } else {
            LogUtil.i("wytings", "------------- Build.VERSION.SDK_INT < 23 ------------");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 300) {
            LogUtil.i("wytings", "--------------requestCode == 300->" + requestCode + "," + permissions.length + "," + grantResults.length);
        } else {
            LogUtil.i("wytings", "--------------requestCode != 300->" + requestCode + "," + permissions + "," + grantResults);
        }
    }


    public static Intent createIntent(Context context, String uid) {

        Intent intent = new Intent(context, UserInfoEditActivity.class);
        intent.putExtra("uid", uid);

        return intent;
    }
}
