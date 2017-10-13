package com.youzi.okredoo.gift;

import android.app.Activity;

import com.youzi.okredoo.model.Gift;

import java.util.ArrayList;

/**
 * Created by KathLine on 2017/1/8.
 */

public class GiftControl implements GiftFrameLayout.DismissListener {

    private static final String TAG = "GiftControl";
    /**
     * 礼物队列
     */
    private ArrayList<Gift> mGiftQueue;

    /**
     * 礼物1
     */
    private GiftFrameLayout mFirstItemGift;

    /**
     * 礼物2
     */
    private GiftFrameLayout mSecondItemGift;

    public GiftControl(GiftFrameLayout giftFrameLayout1, GiftFrameLayout giftFrameLayout2) {
        mGiftQueue = new ArrayList<>();
        setGiftLayout(giftFrameLayout1, giftFrameLayout2);
    }

    public void setGiftLayout(GiftFrameLayout giftFrameLayout1, GiftFrameLayout giftFrameLayout2) {
        mFirstItemGift = giftFrameLayout1;
        mSecondItemGift = giftFrameLayout2;

        mFirstItemGift.setDismissListener(this);
        mSecondItemGift.setDismissListener(this);
    }

    public void loadGift(Gift gift) {
        loadGift(gift, true);
    }

    /**
     * 加入礼物，具有实时连击效果
     *
     * @param gift         礼物
     * @param supportCombo 是否支持实时连击，如果为true：支持，否则不支持
     */
    public void loadGift(Gift gift, boolean supportCombo) {
        if (supportCombo) {
            if (mFirstItemGift.isShowing()) {
                if (mFirstItemGift.getCurrentGiftId().equals(gift.getGid()) && mFirstItemGift.getCurrentSendUserId().equals(gift.getSendUserId())) {
                    //连击
                    //LogUtil.i(TAG, "addGiftQueue: ========mFirstItemGift连击========礼物：" + gift.getGid() + ",连击X" + gift.getGiftCount());
                    mFirstItemGift.appendGift(gift.getGiftCount());
                    return;
                }
            }

            if (mSecondItemGift.isShowing()) {
                if (mSecondItemGift.getCurrentGiftId().equals(gift.getGid()) && mSecondItemGift.getCurrentSendUserId().equals(gift.getSendUserId())) {
                    //连击
                    //LogUtil.i(TAG, "addGiftQueue: ========mSecondItemGift连击========礼物：" + gift.getGid() + ",连击X" + gift.getGiftCount());
                    mSecondItemGift.appendGift(gift.getGiftCount());
                    return;
                }
            }
        }

        addGiftQueue(gift, supportCombo);
    }

    private void addGiftQueue(final Gift gift, final boolean supportCombo) {
        if (mGiftQueue.size() == 0) {
            mGiftQueue.add(gift);
            showGift();
            return;
        }
        if (supportCombo) {
            for (Gift model : mGiftQueue) {
                if (model.getGid().equals(gift.getGid()) && model.getSendUserId().equals(gift.getSendUserId())) {
                    model.setGiftCount(model.getGiftCount() + gift.getGiftCount());
                    return;
                }
            }
        }
        mGiftQueue.add(gift);
    }

    /**
     * 显示礼物
     */
    private synchronized void showGift() {
        if (isEmpty()) {
            return;
        }
        Activity activity = (Activity) mFirstItemGift.getContext();
        if (activity.isDestroyed() || activity.isFinishing()) {
            return;
        }

        if (!mFirstItemGift.isShowing() && mFirstItemGift.isEnd()) {
            Gift gift = getGift();
            if (gift != null) {
                mFirstItemGift.setGift(gift);
                mFirstItemGift.startAnimation();
            }
        }
        if (!mSecondItemGift.isShowing() && mSecondItemGift.isEnd()) {
            Gift gift = getGift();
            if (gift != null) {
                mSecondItemGift.setGift(gift);
                mSecondItemGift.startAnimation();
            }
        }
    }

    /**
     * 取出礼物
     */
    private synchronized Gift getGift() {
        return mGiftQueue.isEmpty() ? null : mGiftQueue.remove(0);
    }


    @Override
    public void dismiss(GiftFrameLayout giftFrameLayout) {
        showGift();
    }

    /**
     * 清除所有礼物
     */
    public synchronized void cleanAll() {
        if (mGiftQueue != null) {
            mGiftQueue.clear();
        }
        if (mFirstItemGift != null) {
            mFirstItemGift.clearHandler();
            mFirstItemGift = null;
        }
        if (mSecondItemGift != null) {
            mSecondItemGift.clearHandler();
            mSecondItemGift = null;
        }
    }

    /**
     * 礼物是否为空
     */
    public synchronized boolean isEmpty() {
        return mGiftQueue == null || mGiftQueue.size() == 0;
    }
}
