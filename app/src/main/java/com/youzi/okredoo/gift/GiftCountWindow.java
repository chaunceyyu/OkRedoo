package com.youzi.okredoo.gift;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.youzi.okredoo.R;

public class GiftCountWindow extends BottomPushPopupWindow<Void> implements OnClickListener {

    private View view;
    ScrollView bottom;

    private LinearLayout llOne;
    private LinearLayout llTwo;
    private LinearLayout llThree;
    private LinearLayout llFour;
    private LinearLayout llFive;
    private LinearLayout llSix;
    private LinearLayout llSeven;
    private LinearLayout llEight;
    private LinearLayout llCus;

    private GiftWindow giftWindow;

    public GiftCountWindow(Activity activity) {
        super(activity, activity, null);
    }




    @Override
    protected View generateCustomView(Void aVoid) {
        view = RelativeLayout.inflate(context, R.layout.pop_gift_count, null);
        bottom = (ScrollView) view.findViewById(R.id.bottom);
        view.findViewById(R.id.pop_layout).setOnClickListener(this);



        llOne = (LinearLayout) view.findViewById(R.id.llOne);
        llTwo = (LinearLayout) view.findViewById(R.id.llTwo);
        llThree = (LinearLayout) view.findViewById(R.id.llThree);
        llFour = (LinearLayout) view.findViewById(R.id.llFour);
        llFive = (LinearLayout) view.findViewById(R.id.llFive);
        llSix = (LinearLayout) view.findViewById(R.id.llSix);
        llSeven = (LinearLayout) view.findViewById(R.id.llSeven);
        llEight = (LinearLayout) view.findViewById(R.id.llEight);
        llCus = (LinearLayout) view.findViewById(R.id.llCus);


        llOne.setOnClickListener(this);
        llTwo.setOnClickListener(this);
        llThree.setOnClickListener(this);
        llFour.setOnClickListener(this);
        llFive.setOnClickListener(this);
        llSix.setOnClickListener(this);
        llSeven.setOnClickListener(this);
        llEight.setOnClickListener(this);
        llCus.setOnClickListener(this);
        return view;
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_layout:
                dismiss();
                break;
            case R.id.llOne:
                giftWindow.setGiftCount("连发");
                dismiss();
                break;
            case R.id.llTwo:
                giftWindow.setGiftCount("10");
                dismiss();
                break;
            case R.id.llThree:
                giftWindow.setGiftCount("66");
                dismiss();
                break;
            case R.id.llFour:
                giftWindow.setGiftCount("99");
                dismiss();
                break;
            case R.id.llFive:
                giftWindow.setGiftCount("188");
                dismiss();
                break;
            case R.id.llSix:
                giftWindow.setGiftCount("520");
                dismiss();
                break;
            case R.id.llSeven:
                giftWindow.setGiftCount("1314");
                dismiss();
                break;
            case R.id.llEight:
                giftWindow.setGiftCount("3344");
                dismiss();
                break;
            case R.id.llCus:
                customCountWin();
                dismiss();
                break;

            default:
                break;
        }
    }

    private void customCountWin() {
        GiftCusCountWindow pop = new GiftCusCountWindow(activity);
        pop.setGiftWindow(giftWindow);
        pop.show(activity);
    }

    public void setGiftWindow(GiftWindow giftWindow) {
        this.giftWindow = giftWindow;
    }

}
