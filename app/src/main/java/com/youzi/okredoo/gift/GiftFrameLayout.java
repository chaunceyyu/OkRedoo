package com.youzi.okredoo.gift;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youzi.okredoo.R;
import com.youzi.okredoo.model.Gift;
import com.youzi.okredoo.util.StringUtil;
import com.youzi.okredoo.util.WidgetUtil;


public class GiftFrameLayout extends FrameLayout implements Handler.Callback {

    //private static final String TAG = "GiftFrameLayout";
    private Context mContext;
    private Handler mHandler = new Handler(this);
    private static final int RESTART_GIFT_ANIMATION_CODE = 1002;
    /**
     * 礼物展示时间
     */
    public static final int GIFT_DISMISS_TIME = 3000;
    /**
     * 当前动画runnable
     */
    private Runnable mDismissRunnable;

    private RelativeLayout anim_rl;
    private ImageView anim_gift, anim_header;
    private TextView anim_nickname, anim_sign;
    private TextView anim_num;

    private Gift mGift;
    /**
     * 礼物连击数
     */
    private int mGiftCount;
    /**
     * 当前播放连击数
     */
    private int mCombo = 1;

    /**
     * 连击增加量
     */
    private int mStep = 1;
    /**
     * 礼物动画正在显示，在这期间可触发连击效果
     */
    private boolean isShowing = false;
    /**
     * 礼物动画结束
     */
    private boolean isEnd = true;
    private DismissListener mDismissListener;

    public GiftFrameLayout(Context context) {
        this(context, null);
    }

    public GiftFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.anim_gift, this, false);
        anim_rl = (RelativeLayout) view.findViewById(R.id.infoRl);
        anim_gift = (ImageView) view.findViewById(R.id.giftIv);
        anim_num = (TextView) view.findViewById(R.id.animation_num);
        anim_header = (ImageView) view.findViewById(R.id.headIv);
        anim_nickname = (TextView) view.findViewById(R.id.nickNameTv);
        anim_sign = (TextView) view.findViewById(R.id.infoTv);
        addView(view);

        anim_num.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (anim_num.getHeight() == 0 || anim_num.getPaint() == null) {
                    return;
                }
                anim_num.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                LinearGradient mLinearGradient = new LinearGradient(0, 0, 0, anim_num.getHeight(), 0xFFFFE900, 0xFFFF8F00, Shader.TileMode.CLAMP);
                anim_num.getPaint().setShader(mLinearGradient);
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        firstHideLayout();
    }

    private void firstHideLayout() {
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this, alpha);
        animator.setDuration(0);
        animator.start();
    }

    public void setGift(Gift gift) {
        if (gift == null || gift.getGiftCount() <= 0) {
            return;
        }
        setTag(gift.getSendUserId());
        mGift = gift;
        mGiftCount = Math.max(gift.getGiftCount(), gift.getSequence());
        mCombo = Math.max(1, gift.getSequence());
        mStep = Math.max(mGiftCount / 20, 1);
        String name = gift.getSendUserName();
        if (!TextUtils.isEmpty(name)) {
            anim_nickname.setText(StringUtil.trimString(name, 12, "…"));
        }
        if (!TextUtils.isEmpty(gift.getGid())) {
            String info = gift.getName();
            if (!TextUtils.isEmpty(info)) {
                int p = info.indexOf("送了");
                if (p > 0) {
                    info = info.substring(p);
                }
            }
            anim_sign.setText(info);
        }
    }


    /**
     * 增加礼物数量,用于连击效果
     */
    public synchronized void appendGift(int count) {
        mGiftCount += count;
        mStep = Math.max(mGiftCount / 20, 1);
        mGift.setGiftCount(mGiftCount);

        if (mDismissRunnable != null) {
            removeDismissRunnable();
            mHandler.sendEmptyMessage(RESTART_GIFT_ANIMATION_CODE);
        }

        setupBackground();
    }


    private void setAnimNum(int num) {
        SpannableStringBuilder sb = new SpannableStringBuilder("x");
        sb.setSpan(new RelativeSizeSpan(0.7f), 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.append(String.valueOf(num));
        anim_num.setText(sb);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case RESTART_GIFT_ANIMATION_CODE:
                mCombo = Math.min(mCombo + mStep, mGiftCount);
                setAnimNum(mCombo);

                comboAnimation();
                removeDismissRunnable();
                break;
            default:
                break;
        }
        return true;
    }


    interface DismissListener {
        void dismiss(GiftFrameLayout giftFrameLayout);
    }

    public void setDismissListener(DismissListener dismissListener) {
        mDismissListener = dismissListener;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public boolean isEnd() {
        return isEnd;
    }

    /**
     * 获取当前显示礼物发送人id
     */
    public String getCurrentSendUserId() {
        if (mGift != null) {
            return mGift.getSendUserId();
        }
        return null;
    }

    /**
     * 获取当前显示礼物id
     */
    public String getCurrentGiftId() {
        if (mGift != null) {
            return mGift.getGid();
        }
        return null;
    }

    /**
     * 显示完连击数与动画时,关闭此Item Layout,并通知外部隐藏自身(供内部调用)
     */
    private void dismissGiftLayout() {
        removeDismissRunnable();

        //动画结束，这时不能触发连击动画
        isShowing = false;
        AnimatorSet animatorSet = endAnmation();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //动画完全结束
                isEnd = true;
                if (mDismissListener != null) {
                    mDismissListener.dismiss(GiftFrameLayout.this);
                }
            }
        });
    }

    private void removeDismissRunnable() {
        if (mDismissRunnable != null) {
            mHandler.removeCallbacks(mDismissRunnable);
            mDismissRunnable = null;
        }
    }

    public void clearHandler() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;//这里要置位null，否则当前页面销毁时，正在执行的礼物动画会造成内存泄漏
        mDismissListener = null;
    }

    public AnimatorSet startAnimation() {

        if (((Activity) mContext).isDestroyed()) {
            return null;
        }

        setupBackground();

        Glide.with(mContext).load(mGift.getSendUserPic()).into(anim_header);
//        ImageUtils.display150(anim_header, mGift.getSendUserPic());
        if (!TextUtils.isEmpty(mGift.getIcon())) {
            Glide.with(mContext).load(mGift.getIcon()).into(anim_gift);
//            ImageUtils.displayImagePure(anim_gift, mGift.getIcon());
        }
        anim_gift.setVisibility(INVISIBLE);
        anim_num.setVisibility(INVISIBLE);

        //布局飞入
        ObjectAnimator flyFromLtoR = GiftAnimationUtil.createFlyFromLtoR(anim_rl, -getWidth(), 0, 400, new OvershootInterpolator());
        flyFromLtoR.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                setVisibility(View.VISIBLE);
                setAlpha(1f);
                isShowing = true;
                isEnd = false;
                setAnimNum(mCombo);
            }
        });

        //礼物飞入
        ObjectAnimator flyFromLtoR2 = GiftAnimationUtil.createFlyFromLtoR(anim_gift, -getWidth(), 0, 300, new DecelerateInterpolator());
        flyFromLtoR2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                anim_gift.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                anim_num.setVisibility(View.VISIBLE);
                comboAnimation();
            }
        });
        return GiftAnimationUtil.startAnimation(flyFromLtoR, flyFromLtoR2);
    }

    private void comboAnimation() {
        ObjectAnimator scaleGiftNum = GiftAnimationUtil.scaleGiftNum(anim_num);
        scaleGiftNum.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mHandler != null) {
                    if (mGiftCount > mCombo) {//连击
                        mHandler.sendEmptyMessage(RESTART_GIFT_ANIMATION_CODE);
                    } else {
                        mDismissRunnable = new Runnable() {
                            @Override
                            public void run() {
                                dismissGiftLayout();
                            }
                        };
                        mHandler.postDelayed(mDismissRunnable, GIFT_DISMISS_TIME);
                    }
                }
            }
        });
        scaleGiftNum.start();
    }

    public AnimatorSet endAnmation() {
        //向上渐变消失
        ObjectAnimator fadeAnimator = GiftAnimationUtil.createFadeAnimator(this, 0, -100, 400, 0);
        fadeAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                anim_num.setVisibility(View.INVISIBLE);
                setVisibility(View.INVISIBLE);
            }
        });
        // 复原
        ObjectAnimator fadeAnimator2 = GiftAnimationUtil.createFadeAnimator(this, 100, 0, 0, 0);

        return GiftAnimationUtil.startAnimation(fadeAnimator, fadeAnimator2);
    }


    private static final int[] BG_THRESHOLDS = {0, 10, 20, 30, 40, 50, 100};
    private static final int[][] COLORS = {
            {0x4C000000, 0x4C000000},
            {0x20FFF573, 0x99FFE740},
            {0x20FF9C00, 0x99FF9C00},
            {0x20A6EE5D, 0x99A6EE5D},
            {0x2032C1F9, 0x9932C1F9},
            {0x20ABA1FD, 0x99FF3692},
            {0x20FAC563, 0x99FF415E}};
    private static final int[] STROKES = {0xFFFFE73F, 0xFFFFE73F, 0xFFFF903B, 0xFFA6EE5D, 0xFF32C1F9, 0xFFA79DFD, 0xFFFAD961};
    private int lastBgIndex = -1;

    //设置背景色
    private void setupBackground() {
        int bgIndex = BG_THRESHOLDS.length;
        while (--bgIndex >= 0) {
            if (mGiftCount >= BG_THRESHOLDS[bgIndex]) {
                break;
            }
        }
        if (bgIndex == lastBgIndex) {
            return;
        }
        lastBgIndex = bgIndex;

        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, COLORS[bgIndex]);
        drawable.setStroke(WidgetUtil.dip2px(getContext(), 1), STROKES[bgIndex]);
        drawable.setCornerRadius(anim_rl.getHeight() / 2);
        anim_rl.setBackground(drawable);
    }
}