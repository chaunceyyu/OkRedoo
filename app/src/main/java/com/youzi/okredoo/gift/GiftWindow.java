package com.youzi.okredoo.gift;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.youzi.okredoo.App;
import com.youzi.okredoo.R;
import com.youzi.okredoo.data.DBManager;
import com.youzi.okredoo.model.Gift;
import com.youzi.okredoo.model.GiftPayMent;
import com.youzi.okredoo.model.Other;
import com.youzi.okredoo.model.OtherUserInfo;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.net.Api;
import com.youzi.okredoo.net.ApiCallback;
import com.youzi.okredoo.net.RequestUtils;
import com.youzi.okredoo.net.ResponseCallBack;
import com.youzi.okredoo.net.ServiceException;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 礼物 Window
 */
public class GiftWindow extends BottomPushPopupWindow<Void> implements OnClickListener {

    private static final String TAG = "GiftWindow";

    private GiftControl giftControl;


    private View view;
    private ScrollView bottom;


    private FrameLayout rootView;
    private RelativeLayout vCover;
    private LinearLayout llGift;

    private int giftViewCount = 0; //礼物总页数
    private ViewPager viewPager;

    private ArrayList<GridView> giftViews; //礼物列表 View
    private ImageView[] pointRootViews;

    // 包裹小圆点的LinearLayout
    private LinearLayout llPointView;

    private List<Gift> giftList = new ArrayList<Gift>();

    private TextView tvCoin;
    private TextView tvPay;
    private TextView tvCount;
    private TextView tvSend;
    private LinearLayout llRight;


    private LinearLayout llCount;
    private LinearLayout llHongbao;

    private Gift choiseGift = null;

    private String tid;
    private String receiveId;
    private String targetId;
    private int mtype = 0;

    private OtherUserInfo anchor;

    private OnSendGiftClickListener onSendGiftClickListener;

    public final static int LIVE = 0;
    public final static int CIRCLE = 1;
    public final static int PRIVATECHAT = 2;
    private GiftComboView tvCombo;

    private GiftListener giftLinstener;

    //送礼个数
    private int count;

    //送礼连击数
    private int combo;


    private User mUser;

    public GiftWindow(Activity activity, User user, String tid, String receiveId, String targetId, int type, GiftControl giftControl) {
        super(activity, activity, null);
        this.tid = tid;
        this.receiveId = receiveId;
        this.targetId = targetId;
        this.mtype = type;
        mUser = user;
        this.giftControl = giftControl;
        hasMask = false;
        loadData();
    }

    public GiftWindow(Activity activity, User user, String tid, String uid, int type) {
        super(activity, activity, null);
        this.tid = tid;
        this.targetId = uid;
        this.mtype = type;
        mUser = user;
        hasMask = false;
        loadData();
    }

    void loadData() {
        tvCoin.setText(mUser.getCoins());
        userCoins(new ApiCallback<Other>() {
            @Override
            public void onSuccess(Other data) {
                tvCoin.setText(data.getCoins());
                mUser.setCoins(data.getCoins());
                DBManager.getInstance().updateUser(mUser);
            }
        });
    }

    public void setAnchor(OtherUserInfo anchor) {
        this.anchor = anchor;
    }

    @Override
    protected View generateCustomView(Void aVoid) {
        view = View.inflate(context, R.layout.pop_gift, null);
        bottom = (ScrollView) view.findViewById(R.id.bottom);
        rootView = (FrameLayout) view.findViewById(R.id.pop_layout);
        rootView.setOnClickListener(this);

        initView();
        return view;
    }

    public void initView() {
        vCover = (RelativeLayout) view.findViewById(R.id.vCover);
        llGift = (LinearLayout) view.findViewById(R.id.llGift);
        tvCoin = (TextView) view.findViewById(R.id.tvCoin);
        tvPay = (TextView) view.findViewById(R.id.tvPay);
        tvCount = (TextView) view.findViewById(R.id.tvCount);
        tvSend = (TextView) view.findViewById(R.id.tvSend);
//        tvPay.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
////                ForwardUtils.target(activity, Constant.ACCOUNT);
//            }
//        });
        llRight = (LinearLayout) view.findViewById(R.id.llRight);
        tvCombo = (GiftComboView) view.findViewById(R.id.tvLianfa);
        tvCombo.setOnClickListener(this);

        if (App.giftList.isEmpty()) {
            App.loadGiftList(new ApiCallback() {
                @Override
                public void onSuccess(Object data) {
                    initGiftPanel();
                }
            });
        } else {
            initGiftPanel();
        }

        llCount = (LinearLayout) view.findViewById(R.id.llCount);
        llCount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                choiseNum();
            }
        });

        llHongbao = (LinearLayout) view.findViewById(R.id.llHongbao);
        llHongbao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != giftLinstener) {
                    giftLinstener.showHongbao();
                    dismiss();

                }
            }
        });
        tvSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSend();
            }
        });

        //调整popwin高度
        view.findViewById(R.id.rlGiftList).getLayoutParams().height = App.screenWidth / 2 + DensityUtil.dip2px(16);
    }

    public void userCoins(final ApiCallback<Other> apiCallback) {
        Map<String, String> params = new HashMap<>();
        RequestUtils.sendPostRequest(Api.USER_COINS, mUser.getUid(), mUser.getToken(), params, new ResponseCallBack<Other>() {
            @Override
            public void onSuccess(Other data) {
                super.onSuccess(data);
                apiCallback.onSuccess(data);
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                apiCallback.onFailure(e);
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public void clickSend() {
        if (choiseGift == null) {
            showToast("请先选择礼物");
            return;
        }
        if ("连发".equals(tvCount.getText().toString())) {    //可以批量发送，但是没有选择，手动连击
            startCombo();
            count = 0;
            combo = 0;
            clickCombo();
        } else {
            count = Integer.parseInt(tvCount.getText().toString());
            if (mtype == LIVE) {
                //直播间
                if (!TextUtils.isEmpty(choiseGift.getReciveAnim())) {
                    sendGift(true);
                } else {
                    sendGift(false);
                }
            } else {
                //动态，动态详情页
                sendGift(false);
            }

        }
    }

    private void startCombo() {
        llRight.setVisibility(View.GONE);
        tvCombo.setVisibility(View.VISIBLE);
        tvCombo.reset();
        countDownCombo();
    }

    private void stopCombo() {
        cancelCombo();
        llRight.setVisibility(View.VISIBLE);
        tvCombo.setVisibility(View.GONE);
        if (count > 0) {
            if (mtype == GiftWindow.PRIVATECHAT) {
                sendGift(false);
            }/* else {
                sendGift(false, callBack);
            }*/
        }
        count = 0;
    }

    private void countDownCombo() {
        ValueAnimator animator = ValueAnimator.ofInt(360, 0).setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int angle = (int) animation.getAnimatedValue();
                tvCombo.setSweepAngle(angle);
                SpannableStringBuilder sb = new SpannableStringBuilder("连发");
                sb.setSpan(new RelativeSizeSpan(0.6f), 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sb.append('\n').append(String.valueOf(angle / 12));
                tvCombo.setText(sb);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                if (tvCombo.getTag() != null) {
                    stopCombo();
                }
            }
        });
        animator.setTarget(tvCombo);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        tvCombo.setTag(animator);
    }

    private void cancelCombo() {
        if (tvCombo.getTag() != null) {
            ValueAnimator animator = (ValueAnimator) tvCombo.getTag();
            tvCombo.setTag(null);
            animator.cancel();      //会触发onAnimationEnd事件
        }
    }

    private void resetCombo() {
        cancelCombo();
        countDownCombo();
    }

    private void clickCombo() {
        combo++;
        if (mtype == GiftWindow.PRIVATECHAT) {
            resetCombo();
            count++;
        } else {
            resetCombo();
            count = 1;
            sendGift(false);
        }
    }


    private void sendGift(final boolean isBigAnim) {
        //当前余额
        long myMoney;
        try {
            myMoney = Long.parseLong(tvCoin.getText().toString());
        } catch (Exception e) {
            showToast("送礼失败，未获取到余额");
            return;
        }

        //礼物价格
        int giftPrice;
        int costMoney;
        try {
            giftPrice = Integer.parseInt(choiseGift.getPrice());
            costMoney = giftPrice * count;
        } catch (Exception e) {
            showToast("送礼失败，未获取到礼物价格");
            return;
        }

        if (myMoney < costMoney) {
            showToast("金币不够了");
            return;
        }

        tvCoin.setText(String.valueOf(myMoney - costMoney));
        //先执行动画
        if (isBigAnim) {
            if (giftLinstener != null) {
                giftLinstener.sendBigGiftAnim(choiseGift);
            }
        } else {
            if (mtype != GiftWindow.PRIVATECHAT) {
                Gift gift = new Gift(choiseGift.getGid(), "我送了" + choiseGift.getName(), count, choiseGift.getIcon(), mUser.getUid(),
                        mUser.getNickName(), mUser.getPhoto(), System.currentTimeMillis());
                if (giftControl != null) {
                    giftControl.loadGift(gift);
                }
            }
        }

        //消费
        String chatType = "";
        if (mtype != GiftWindow.PRIVATECHAT) {
            if (mtype == GiftWindow.LIVE) {
                chatType = "4";
            } else if (mtype == GiftWindow.CIRCLE) {
                chatType = "1";
            }
            final Gift gift = choiseGift;
            final int count1 = count;
            final String cost = String.valueOf(costMoney);
            costMoneyByGift(cost, String.valueOf(count), String.valueOf(combo), choiseGift.getGid(), tid, String.valueOf(mtype),
                    chatType, targetId, receiveId, gift.getName(), anchor, new ApiCallback<Other>() {
                        @Override
                        public void onSuccess(Other data) {
                            if (null != mUser) {
                                mUser.setCoins(data.getAmount());
                                DBManager.getInstance().updateUser(mUser);
                                tvCoin.setText(data.getAmount());
                                if (null != giftLinstener) {
                                    giftLinstener.sendGiftOk(gift, count1, cost);
                                }
                                if (isBigAnim) {
                                    dismiss();
                                }
                            }
                        }
                    });
        } else {
            GiftPayMent gpm = new GiftPayMent();
            gpm.setAmount(String.valueOf(costMoney));
            gpm.setCount(String.valueOf(count));
            gpm.setChatType("1");
            gpm.setType(String.valueOf(mtype));
            gpm.setTid(tid);
            gpm.setGid(choiseGift.getGid());
            gpm.setTargetid(targetId);
            if (null != onSendGiftClickListener) {
                onSendGiftClickListener.toSendGift(gpm, choiseGift);
                dismiss();
            }
        }

    }

    /**
     * 送礼物
     *
     * @param amount
     * @param count
     * @param gid
     * @param tid
     * @param type
     * @param conversationType
     * @param targetid
     * @param receiveId
     * @param apiCallback
     */
    public void costMoneyByGift(String amount, String count, String combo, String gid, String tid, String type, String conversationType,
                                String targetid, String receiveId, String giftname, OtherUserInfo anchor,
                                final ApiCallback<Other> apiCallback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("amount", amount);
        params.put("conversationType", conversationType);
        params.put("count", count);
        params.put("sequence", combo);
        params.put("gid", gid);
        params.put("targetid", targetid);
        params.put("receiveId", receiveId);
        params.put("tid", tid);
        params.put("type", type);
        params.put("anchorname", anchor == null ? "" : anchor.getNickName());   //以下几个都是后端统计需要 orz
        params.put("anchorlevel", anchor == null ? "" : anchor.getGrade());
        params.put("giftname", giftname == null ? "" : giftname);

        RequestUtils.sendPostRequest(Api.SEND_GIFT, mUser.getUid(), mUser.getToken(), params, new ResponseCallBack<Other>() {

            @Override
            public void onSuccess(Other data) {
                apiCallback.onSuccess(data);
            }

            @Override
            public void onFailure(ServiceException e) {
                apiCallback.onFailure(e);
            }
        });
    }


    public void showHongbao() {
        llHongbao.setVisibility(View.VISIBLE);
    }


    /**
     * 显示数量选择控件
     */
    public void showChoiseNum() {
        //llCount.setVisibility(View.VISIBLE);      //隐藏连发
        tvCount.setText("连发");
    }

    /**
     * 隐藏数量选择控件
     */
    public void hideChoiseNum() {
        //llCount.setVisibility(View.GONE);
        tvCount.setText("1");

    }


    private void choiseNum() {
        if (choiseGift == null) {
            showToast("请先选择礼物");
        } else {
            GiftCountWindow pop = new GiftCountWindow(activity);
            pop.setGiftWindow(GiftWindow.this);
            pop.show(activity);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_layout:
                dismiss();
                break;
            case R.id.tvLianfa:
                clickCombo();
                break;
            default:
                break;
        }
    }

    private void initGiftPanel() {
        giftList = App.giftList;
        giftViewCount = giftList.size() % 8 == 0 ? giftList.size() / 8 : giftList.size() / 8 + 1;

        giftViews = new ArrayList<>();
        for (int i = 0; i < giftViewCount; i++) {  //添加礼物页数
            GridView gvGiftView = (GridView) LayoutInflater.from(activity).inflate(R.layout.gift_list_view, null);
            gvGiftView.setNumColumns(4);
            final GiftAdapter giftAdapter = new GiftAdapter(activity, GiftWindow.this);
            gvGiftView.setAdapter(giftAdapter);
            giftAdapter.addList(getGiftPagerData(i, 8));
            giftViews.add(gvGiftView);

        }


        llPointView = (LinearLayout) view.findViewById(R.id.llPointView);
        viewPager = (ViewPager) view.findViewById(R.id.guidePages);


        //有几张图片下面就显示几个小圆点
        pointRootViews = new ImageView[giftViews.size()];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
        lp.setMargins(0, 0, 10, 0);
        for (int i = 0; i < giftViews.size(); i++) {
            ImageView imageView = new ImageView(activity);
            imageView.setImageResource(i == 0 ? R.drawable.point_check : R.drawable.point_uncheck);
            llPointView.addView(imageView, lp);
            pointRootViews[i] = imageView;
        }

        // 给viewpager设置适配器
        viewPager.setAdapter(new GuidePageAdapter());
        // 给viewpager设置监听事件
        viewPager.addOnPageChangeListener(new GuidePageChangeListener());
    }

    /**
     * 获取指定页码的 Gift
     *
     * @param num        页码 0开始
     * @param numPerPage 页容量 eg：8
     * @return
     */
    private List<Gift> getGiftPagerData(int num, int numPerPage) {
        int minIndex = num * numPerPage;
        int maxIndex = (num + 1) * numPerPage;
        if (maxIndex > giftList.size()) {
            maxIndex = giftList.size();
        }
        return giftList.subList(minIndex, maxIndex);
    }

    /**
     * 设置选中礼物
     *
     * @param choiseGift
     */
    public void setChoiseGift(Gift choiseGift) {
        if (tvCombo.getTag() != null && !this.choiseGift.getGid().equals(choiseGift.getGid())) {
            stopCombo();
        }
        this.choiseGift = choiseGift;
    }

    public void setGiftCount(String giftCount) {
        tvCount.setText(giftCount);
    }


    // 指引页面数据适配器
    private class GuidePageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return giftViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(giftViews.get(arg1));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(giftViews.get(arg1));
            return giftViews.get(arg1);
        }

    }


    // 指引页面更改事件监听器
    private class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            // 遍历数组，清除选定状态，让当前页面的小圆点设置颜色
            for (int i = 0; i < pointRootViews.length; i++) {
                ((GiftAdapter) giftViews.get(i).getAdapter()).clearSelected();
                pointRootViews[i].setImageResource(position == i ? R.drawable.point_check : R.drawable.point_uncheck);
            }
        }

    }


    public static class GiftListener {
        //发送礼物成功
        public void sendGiftOk(Gift gift, int count, String incre) {
        }

        //发送大礼物动画
        public void sendBigGiftAnim(Gift gift) {
        }

        //显示红包
        public void showHongbao() {
        }
    }

    public void setGiftLinstener(GiftListener giftLinstener) {
        this.giftLinstener = giftLinstener;
    }


    public interface OnSendGiftClickListener {
        void toSendGift(GiftPayMent gpm, Gift choiseGift);
    }

    public void setOnSendGiftClickListener(OnSendGiftClickListener onSendGiftClickListener) {
        this.onSendGiftClickListener = onSendGiftClickListener;
    }
}
